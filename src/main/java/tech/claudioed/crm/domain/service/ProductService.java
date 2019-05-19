package tech.claudioed.crm.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.Order;

@Service
@Slf4j
public class ProductService {

  private final Connection connection;

  private final ObjectMapper objectMapper;

  public ProductService(Connection connection, ObjectMapper objectMapper) {
    this.connection = connection;
    this.objectMapper = objectMapper;
  }

  @SneakyThrows
  @Async
  void analyzeOrder(@NonNull final Order order) {
    log.info("Received {} to be analyzed", order);
    connection.publish("product-analyze-topic", objectMapper.writeValueAsBytes(order));
  }

}