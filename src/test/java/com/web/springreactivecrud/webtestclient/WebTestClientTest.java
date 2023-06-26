package com.web.springreactivecrud.webtestclient;


import com.web.springreactivecrud.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class WebTestClientTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void stepVerifierTest(){
    Flux<Response> responseFlux = this.webTestClient
        .get()
        .uri("/api/v2/square/{input}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .returnResult(Response.class)
        .getResponseBody();

    StepVerifier.create(responseFlux)
        .expectNextMatches(r -> r.getOutput() == 25)
        .verifyComplete();
  }

  @Test
  public void fluentAssertion(){
    this.webTestClient
        .get()
        .uri("/api/v2/square/{input}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(Response.class)
        .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
  }
}
