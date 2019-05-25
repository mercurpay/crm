package tech.claudioed.crm.domain.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;

@Slf4j
@Service("product-unavailable")
public class ProductUnavailableEventHandler extends AbstractOrderEventHandler implements OrderEventHandler {

  public ProductUnavailableEventHandler(OrderRepository orderRepository) {
    super(orderRepository);
  }

  @Override
  public Event handle(String orderId, EventRequest eventRequest) {
    log.info("Product unavailable event for order id {} ", orderId);
    final Event event = create(eventRequest);
    return persist(orderId,event);
  }

}
