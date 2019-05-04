package tech.claudioed.crm.domain.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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

/** @author claudioed on 2019-03-05. Project crm */
@Slf4j
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  private final BeanFactory factory;

  public OrderService(OrderRepository orderRepository,
      BeanFactory factory) {
    this.orderRepository = orderRepository;
    this.factory = factory;
  }

  public Order find(String id) {
    final Optional<Order> orderOptional = this.orderRepository.findById(id);
    if(orderOptional.isPresent()){
      return orderOptional.get();
    }
    log.error("Order id " + id + " not found");
    throw new OrderNotFound("Order id " + id + " not found");
  }

  public Order newOrder(@NonNull NewOrderRequest request) {
    Set<Event> events = new HashSet<>();
    events.add(Event.created());
    final Order order =
        Order.builder()
            .customerId(request.getCustomerId())
            .value(request.getValue())
            .id(UUID.randomUUID().toString())
            .events(events)
            .build();
    return this.orderRepository.save(order);
  }

  public Event addEvent(String id, EventRequest eventRequest) {
    log.info("Receiving new order event order id {} type {}",id,eventRequest.getType());
    return factory.getBean(eventRequest.getType().toLowerCase(), OrderEventHandler.class).handle(id,eventRequest);
  }

}
