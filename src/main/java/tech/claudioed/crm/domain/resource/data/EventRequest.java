package tech.claudioed.crm.domain.resource.data;

import java.util.Map;
import lombok.Data;

/**
 * @author claudioed on 2019-03-05.
 * Project crm
 */
@Data
public class EventRequest {

  private String type;

  private Map<String,Object> data;

}
