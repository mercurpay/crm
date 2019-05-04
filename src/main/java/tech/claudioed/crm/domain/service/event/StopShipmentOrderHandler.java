package tech.claudioed.crm.domain.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.data.StopShipmentEvent;
import tech.claudioed.crm.domain.service.event.sender.StopShipmentEventSender;

/** @author claudioed on 2019-04-13. Project crm */
@Slf4j
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
    log.info("Receiving fraud event. Starting serialization for stop shipment process...");
    final StopShipmentEvent stopShipmentEvent = StopShipmentEvent.builder().orderId(orderId)
        .data(eventRequest.getData()).build();
    final Event stopShipment = create("STOP_SHIPMENT_REQUEST",this.mapper.convertValue(stopShipmentEvent, Map.class));
    this.stopShipmentEventSender.send(stopShipment);
    log.info("Stop shipment processed successfully!");
    return persist(orderId,stopShipment);
  }
}
