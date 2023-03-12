package restadapter.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import restadapter.AdapterResponseUtil;
import restadapter.configuration.SupplierConfig;
import restadapter.mapper.PurchaseMapper;

public class PurchaseServiceTest {

  @Test
  public void testSendToSupplier_whenValid_thenReturnResponse() throws JsonProcessingException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Correlation-Id", "correlation");
    HttpEntity<String> request =
        new HttpEntity<String>(
            "{\"id\":\"PUR0000001\",\"items\":[{\"quantity\":1,\"id\":\"PRD0000001\"},{\"quantity\":2,\"id\":\"PRD0000002\"}]}",
            headers);

    RestTemplate mock = Mockito.mock(RestTemplate.class);
    when(mock.postForObject("endpoint", request, String.class))
        .thenReturn("{\"id\":\"SUA0000001\",\"purchase_id\":\"PUR0000001\"}");

    RabbitTemplate mockRabbit = Mockito.spy(RabbitTemplate.class);
    doNothing().when(mockRabbit).send(any(), anyString(), any(), any());

    PurchaseService service =
        new PurchaseService(
            mock,
            mockRabbit,
            new SupplierConfig("endpoint"),
            new ObjectMapper(),
            new PurchaseMapper());
    service.sendToSupplier(AdapterResponseUtil.defaultPurchaseRequest(), "correlation");
    verify(mockRabbit).send(any(), anyString(), any(), any());
  }
}
