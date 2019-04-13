package tech.claudioed.crm.domain.service.data;

import java.util.Map;
import javax.xml.soap.SAAJResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 2019-04-13.
 * Project crm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartShipmentEvent {

  private String orderId;

  private Map<String,Object> data;

}
