package tech.claudioed.crm.domain.exception;

/**
 * @author claudioed on 2019-03-05.
 * Project crm
 */
public class OrderNotFound extends RuntimeException {

  public OrderNotFound(String message) {
    super(message);
  }

}
