package tech.claudioed.crm.domain.service.event;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;
import tech.claudioed.crm.domain.exception.OrderNotFound;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;

/** @author claudioed on 2019-03-30. Project crm */
@Slf4j
public abstract class AbstractOrderEventHandler implements OrderEventHandler {

  private final OrderRepository orderRepository;

  public AbstractOrderEventHandler(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  Event persist(String orderId, Event event) {
    final Optional<Order> orderOptional = this.orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      final Order order = orderOptional.get();
      this.orderRepository.save(order.addEvent(event));
      return event;
    }
    log.error("Order id " + orderId + " not found");
    throw new OrderNotFound("Order id " + orderId + " not found");
  }

  Event create(EventRequest request){
    return Event.builder()
        .id(UUID.randomUUID().toString())
        .type(request.getType())
        .at(LocalDateTime.now())
        .data(request.getData())
        .build();
  }

  Event create(String type,Map<String,Object> data){
    return Event.builder()
        .id(UUID.randomUUID().toString())
        .type(type)
        .at(LocalDateTime.now())
        .data(data)
        .build();
  }

}
