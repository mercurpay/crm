package tech.claudioed.crm.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author claudioed on 2019-03-05. Project crm */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  private String id;

  private LocalDateTime at;

  private String type;

  private Map<String, Object> data;

  public static Event created() {
    return Event.builder().id(UUID.randomUUID().toString()).at(LocalDateTime.now()).type("CREATION").data(new HashMap<>()).build();
  }

}
