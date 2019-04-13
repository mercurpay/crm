package tech.claudioed.crm.domain.service.event;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;
import tech.claudioed.crm.domain.exception.OrderNotFound;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.ClaimService;

/** @author claudioed on 2019-03-30. Project crm */
@Slf4j
@Service("fraud")
public class ClaimEventHandler extends AbstractOrderEventHandler implements OrderEventHandler {

  private final ClaimService claimService;

  private final OrderRepository orderRepository;

  public ClaimEventHandler(OrderRepository orderRepository, ClaimService claimService,
      OrderRepository orderRepository1) {
    super(orderRepository);
    this.claimService = claimService;
    this.orderRepository = orderRepository1;
  }

  @Override
  public Event handle(String orderId, EventRequest eventRequest) {
    log.info("New fraud event for order id {} ", orderId);
    final Event event = create(eventRequest);
    final Optional<Order> order = this.orderRepository.findById(orderId);
    if(order.isPresent()){
      final Order orderData = order.get();
      this.claimService.notifyClaim(orderId,orderData.getCustomerId(),event);
      return persist(orderId, event);
    }else {
      log.error("Order id " + orderId + " not found");
      throw new OrderNotFound("Order id " + orderId + " not found");
    }
  }
}
