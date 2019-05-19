package tech.claudioed.crm.domain.service;

import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;
import tech.claudioed.crm.domain.exception.OrderNotFound;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.resource.data.NewOrderRequest;
import tech.claudioed.crm.domain.service.event.OrderEventHandler;

@Slf4j
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  private final BeanFactory factory;

  private final ProductService productService;

  public OrderService(OrderRepository orderRepository,
      BeanFactory factory, ProductService productService) {
    this.orderRepository = orderRepository;
    this.factory = factory;
    this.productService = productService;
  }

  public Order find(String id) {
    final Optional<Order> orderOptional = this.orderRepository.findById(id);
    if (orderOptional.isPresent()) {
      return orderOptional.get();
    }
    log.error("Order id " + id + " not found");
    throw new OrderNotFound("Order id " + id + " not found");
  }

  public Order newOrder(@NonNull final NewOrderRequest request) {
    Order savedOrder = orderRepository.save(request.toOrder());
    productService.analyzeOrder(savedOrder);
    return savedOrder;
  }

  public Event addEvent(String id, EventRequest eventRequest) {
    log.info("Receiving new order event order id {} type {}", id, eventRequest.getType());
    return factory.getBean(eventRequest.getType().toLowerCase(), OrderEventHandler.class)
        .handle(id, eventRequest);
  }

}
