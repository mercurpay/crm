package tech.claudioed.crm.domain.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;
import tech.claudioed.crm.domain.exception.OrderNotFound;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.resource.data.NewOrderRequest;

/** @author claudioed on 2019-03-05. Project crm */
@Slf4j
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
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
    final Event event =
        Event.builder()
            .id(UUID.randomUUID().toString())
            .type(eventRequest.getType())
            .at(LocalDateTime.now())
            .data(eventRequest.getData())
            .build();
    final Optional<Order> orderOptional = this.orderRepository.findById(id);
    if (orderOptional.isPresent()) {
      final Order order = orderOptional.get();
      this.orderRepository.save(order.addEvent(event));
      return event;
    }
    log.error("Order id " + id + " not found");
    throw new OrderNotFound("Order id " + id + " not found");
  }
}
