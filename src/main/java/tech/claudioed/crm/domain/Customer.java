package tech.claudioed.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author claudioed on 2019-04-11. Project crm */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private String id;

  private String address;

  private String city;

  private String country;

  private String email;

}
