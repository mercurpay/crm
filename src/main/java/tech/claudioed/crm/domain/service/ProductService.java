package tech.claudioed.crm.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.claudioed.crm.domain.event.AnalyzeOrderEvent;

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
  void analyzeOrder(final AnalyzeOrderEvent analyzeOrderEvent) {
    log.info("Received {} to be analyzed", analyzeOrderEvent);
    connection.publish("product-analyze-topic", objectMapper.writeValueAsBytes(analyzeOrderEvent));
  }

}