package tech.claudioed.crm.domain.resource;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tech.claudioed.crm.domain.Event;
import tech.claudioed.crm.domain.Order;
import tech.claudioed.crm.domain.resource.data.EventRequest;
import tech.claudioed.crm.domain.resource.data.NewOrderRequest;
import tech.claudioed.crm.domain.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderService orderService;

  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<Order> newOrder(@Valid @RequestBody NewOrderRequest newOrderRequest,
      UriComponentsBuilder uriBuilder){
    final Order order = this.orderService.newOrder(newOrderRequest);
    final UriComponents uriComponents =
        uriBuilder.path("api/orders/{id}").buildAndExpand(order.getId());
    return ResponseEntity.created(uriComponents.toUri()).body(order);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> findOrder(@PathVariable("id")String id){
    return ResponseEntity.ok(this.orderService.find(id));
  }

  @PostMapping("/{id}/events")
  public ResponseEntity<Event> newEvent(@PathVariable("id")String id, @RequestBody EventRequest eventRequest,UriComponentsBuilder uriBuilder){
    final Event event = this.orderService.addEvent(id, eventRequest);
    final UriComponents uriComponents =
        uriBuilder.path("api/orders/{id}/events/{eventId}").buildAndExpand(id,event.getId());
    return ResponseEntity.created(uriComponents.toUri()).body(event);
  }

}
