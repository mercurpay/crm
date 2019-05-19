package tech.claudioed.crm.domain.resource.data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;

@Data
public class NewOrderRequest {

  @NotNull
  private BigDecimal value;

  @NotBlank
  private String customerId;

  @NotBlank
  private String requestId;

  @NotBlank
  private String productId;

  public Order toOrder() {
    Set<Event> events = new HashSet<>();
    events.add(Event.created());

    return Order.builder()
        .id(UUID.randomUUID().toString())
        .value(getValue())
        .customerId(getCustomerId())
        .productId(getProductId())
        .events(events)
        .build();
  }

}