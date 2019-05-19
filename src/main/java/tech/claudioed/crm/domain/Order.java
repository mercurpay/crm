package tech.claudioed.crm.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  private String id;

  private BigDecimal value;

  private String customerId;

  private String productId;

  private Set<Event> events = new HashSet<>();

  public Order addEvent(@NonNull Event event){
    this.events.add(event);
    return this;
  }

}