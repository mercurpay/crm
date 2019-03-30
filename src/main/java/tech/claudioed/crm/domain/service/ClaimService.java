package tech.claudioed.crm.domain.service;

import io.nats.client.Connection;
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

  public ClaimService(@Qualifier("natsConnection") Connection natsConnection) {
    this.natsConnection = natsConnection;
  }

  public void notifyClaim(Event event){
    log.info("Notifying event {}", event.toString());
    final ClaimRequest claim = ClaimRequest.builder().type("claim").data(event.getData()).build();
    this.natsConnection.publish("request-claims",claim.toString().getBytes());
    log.info("Event {} notified successfully", event.getId());
  }

}
