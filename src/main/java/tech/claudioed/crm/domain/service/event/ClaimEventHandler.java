package tech.claudioed.crm.domain.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.ClaimService;

/** @author claudioed on 2019-03-30. Project crm */
@Slf4j
@Service("fraud")
public class ClaimEventHandler extends AbstractOrderEventHandler implements OrderEventHandler {

  private final ClaimService claimService;

  public ClaimEventHandler(OrderRepository orderRepository, ClaimService claimService) {
    super(orderRepository);
    this.claimService = claimService;
  }

  @Override
  public Event handle(String orderId, EventRequest eventRequest) {
    log.info("New fraud event for order id {} ", orderId);
    final Event event = create(eventRequest);
    this.claimService.notifyClaim(orderId,event);
    return persist(orderId, event);
  }
}
