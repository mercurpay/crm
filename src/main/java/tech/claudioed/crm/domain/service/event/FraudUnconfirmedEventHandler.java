package tech.claudioed.crm.domain.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;

/** @author claudioed on 2019-03-30. Project crm */
@Slf4j
@Service("checked")
public class FraudUnconfirmedEventHandler extends AbstractOrderEventHandler implements OrderEventHandler {

  public FraudUnconfirmedEventHandler(OrderRepository orderRepository) {
    super(orderRepository);
  }

  @Override
  public Event handle(String orderId, EventRequest eventRequest) {
    log.info("New approved event for order id {} ", orderId);
    final Event event = create(eventRequest);
    return persist(orderId,event);
  }

}
