package tech.claudioed.crm.domain.resource.data;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author claudioed on 2019-03-05.
 * Project crm
 */
@Data
public class NewOrderRequest {

  private BigDecimal value;

  private String customerId;

  private String requestid;

}
