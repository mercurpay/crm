package tech.claudioed.crm.domain.service.event;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.CustomerService;

/** @author claudioed on 2019-04-13. Project crm */
@Service
public class StopShipmentOrderHandler extends AbstractOrderEventHandler
    implements OrderEventHandler {

  private final CustomerService customerService;

  public StopShipmentOrderHandler(OrderRepository orderRepository,
      CustomerService customerService) {
    super(orderRepository);
    this.customerService = customerService;
  }

  @Override
  public Event handle(@NonNull String orderId, @NonNull EventRequest eventRequest) {

    final Event stopShipment = create(eventRequest);

    return persist(orderId,stopShipment);
  }
}
