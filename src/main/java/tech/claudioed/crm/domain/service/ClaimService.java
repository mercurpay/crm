package tech.claudioed.crm.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.service.data.ClaimRequest;

/**
 * @author claudioed on 2019-03-30.
 * Project crm
 */
@Slf4j
@Service
public class ClaimService {

  private final Connection natsConnection;

  private final ObjectMapper mapper;

  public ClaimService(@Qualifier("natsConnection") Connection natsConnection,
      ObjectMapper mapper) {
    this.natsConnection = natsConnection;
    this.mapper = mapper;
  }

  @SneakyThrows
  public void notifyClaim(String orderId,Event event){
    log.info("Notifying event {}", event.toString());
    final ClaimRequest claim = ClaimRequest.builder().type("claim").orderId(orderId).data(event.getData()).build();
    this.natsConnection.publish("request-claims",this.mapper.writeValueAsString(claim).getBytes());
    natsConnection.flush(Duration.ofSeconds(5));
    log.info("Event {} notified successfully", event.getId());
  }

}
