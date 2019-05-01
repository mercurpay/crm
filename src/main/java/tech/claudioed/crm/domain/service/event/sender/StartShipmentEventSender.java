package tech.claudioed.crm.domain.service.event.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.service.data.StartShipmentEvent;

/** @author claudioed on 2019-04-21. Project crm */
@Service
public class StartShipmentEventSender {

  private final Connection connection;

  private final ObjectMapper mapper;

  public StartShipmentEventSender(@Qualifier("natsConnection") Connection connection, ObjectMapper mapper) {
    this.connection = connection;
    this.mapper = mapper;
  }

  @SneakyThrows
  public void send(Event startShipmentEvent) {
    this.connection.publish("start-shipment", this.mapper.writeValueAsBytes(startShipmentEvent));
  }

}
