package tech.claudioed.crm.infra.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author claudioed on 2019-03-30.
 * Project crm
 */
@Configuration
public class WebProducers {

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
