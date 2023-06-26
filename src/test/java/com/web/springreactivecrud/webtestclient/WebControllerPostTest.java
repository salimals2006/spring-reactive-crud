package com.web.springreactivecrud.webtestclient;

import com.web.springreactivecrud.controller.ReactiveMathController;
import com.web.springreactivecrud.dto.MultiplyRequestDto;
import com.web.springreactivecrud.dto.Response;
import com.web.springreactivecrud.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class WebControllerPostTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private ReactiveMathService reactiveMathService;

  @Test
  public void postTest() {
    Mockito
        .when(reactiveMathService.multiply(Mockito.any()))
        .thenReturn(Mono.just(new Response(1)));

    this.webTestClient
        .post()
        .uri("/api/v2/multiply")
        .accept(MediaType.APPLICATION_JSON)
        .headers(h -> h.setBasicAuth("username", "password"))
        .headers(h -> h.set("some-key", "some-val"))
        .bodyValue(new MultiplyRequestDto())
        .exchange()
        .expectStatus().is2xxSuccessful();
  }


}
