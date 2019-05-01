package tech.claudioed.crm.domain.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.data.StopShipmentEvent;
import tech.claudioed.crm.domain.service.event.sender.StopShipmentEventSender;

/** @author claudioed on 2019-04-13. Project crm */
@Service
public class StopShipmentOrderHandler extends AbstractOrderEventHandler
    implements OrderEventHandler {

  private final StopShipmentEventSender stopShipmentEventSender;

  private final ObjectMapper mapper;

  public StopShipmentOrderHandler(OrderRepository orderRepository,
      StopShipmentEventSender stopShipmentEventSender,
      ObjectMapper mapper) {
    super(orderRepository);
    this.stopShipmentEventSender = stopShipmentEventSender;
    this.mapper = mapper;
  }

  @Override
  @SneakyThrows
  public Event handle(@NonNull String orderId, @NonNull EventRequest eventRequest) {
    final StopShipmentEvent stopShipmentEvent = StopShipmentEvent.builder().orderId(orderId)
        .data(eventRequest.getData()).build();
    final Event stopShipment = create("stopShipment",this.mapper.convertValue(stopShipmentEvent, Map.class));
    this.stopShipmentEventSender.send(stopShipment);
    return persist(orderId,stopShipment);
  }
}
