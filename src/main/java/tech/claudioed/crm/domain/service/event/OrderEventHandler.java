package tech.claudioed.crm.domain.service.event;

import lombok.NonNull;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.resource.data.EventRequest;

/**
 * @author claudioed on 2019-03-30.
 * Project crm
 */
public interface OrderEventHandler {

  Event handle(final @NonNull String orderId,final @NonNull EventRequest eventRequest);

}
