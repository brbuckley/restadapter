package restadapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import restadapter.AdapterApplication;
import restadapter.api.model.purchasems.purchase.PurchaseRequest;
import restadapter.configuration.SupplierConfig;
import restadapter.mapper.PurchaseMapper;

/** Purchase services. */
@AllArgsConstructor
@Service
@Slf4j
public class PurchaseService {

  private final RestTemplate restTemplate;
  private final RabbitTemplate rabbitTemplate;
  private final SupplierConfig config;
  private final ObjectMapper mapper;
  private final PurchaseMapper purchaseMapper;

  /**
   * Sends the adapted purchase to SupplierA.
   *
   * @param purchase Purchase Request.
   * @param correlationId Correlation id.
   * @throws JsonProcessingException Json Processing Exception.
   */
  public void sendToSupplier(PurchaseRequest purchase, String correlationId)
      throws JsonProcessingException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Correlation-Id", correlationId);
    HttpEntity<String> request =
        new HttpEntity<String>(
            mapper.writeValueAsString(purchaseMapper.toSupplierRequest(purchase)), headers);
    String messageBody =
        this.restTemplate.postForObject(config.getEndpoint(), request, String.class);
    log.info("Sending message with id: {} & body: {}", correlationId, messageBody);
    rabbitTemplate.convertAndSend(
        AdapterApplication.MESSAGE_QUEUE,
        messageBody,
        m -> {
          m.getMessageProperties().getHeaders().put("X-Correlation-Id", correlationId);
          return m;
        });
  }
}
