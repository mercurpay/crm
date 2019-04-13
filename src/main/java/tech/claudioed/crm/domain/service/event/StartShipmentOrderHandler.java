package tech.claudioed.crm.domain.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Customer;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.repository.OrderRepository;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.service.CustomerService;
import tech.claudioed.crm.domain.service.data.ApprovedEventData;
import tech.claudioed.crm.domain.service.data.StartShipmentEvent;

/** @author claudioed on 2019-04-13. Project crm */
@Slf4j
@Service
public class StartShipmentOrderHandler extends AbstractOrderEventHandler
    implements OrderEventHandler {

  private final CustomerService customerService;

  private final ObjectMapper mapper;

  public StartShipmentOrderHandler(
      OrderRepository orderRepository, CustomerService customerService, ObjectMapper mapper) {
    super(orderRepository);
    this.customerService = customerService;
    this.mapper = mapper;
  }

  @Override
  @SneakyThrows
  public Event handle(@NonNull String orderId, @NonNull EventRequest eventRequest) {
    log.info("Receiving approved event. Starting serialization...");
    final ApprovedEventData approvedEventData =
        this.mapper.readValue(
            this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventRequest.getData()),
            ApprovedEventData.class);
    log.info("Serialization executed successfully...Getting customer Data...");
    final Customer customerData = this.customerService.customer(approvedEventData.getCustomerId());
    log.info("Serialization executed successfully...Customer Data loaded successfully");
    StartShipmentEvent startShipmentEvent =
        StartShipmentEvent.builder()
            .orderId(orderId)
            .data(this.mapper.convertValue(customerData, Map.class))
            .build();
    final Event startShipment =
        create("startShipment", this.mapper.convertValue(startShipmentEvent, Map.class));
    return persist(orderId, startShipment);
  }
}
