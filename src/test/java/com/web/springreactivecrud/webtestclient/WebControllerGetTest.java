package com.web.springreactivecrud.webtestclient;


import com.web.springreactivecrud.controller.ReactiveMathController;
import com.web.springreactivecrud.dto.Response;
import com.web.springreactivecrud.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@WebFluxTest(controllers = {ReactiveMathController.class})
public class WebControllerGetTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private ReactiveMathService reactiveMathService;

  @Test
  public void singleResponseTest(){

    Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));

    this.webTestClient
        .get()
        .uri("/api/v2/square/{input}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(Response.class)
        .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
  }

  @Test
  public void multipleResponseTest(){
    Flux<Response> flux = Flux.range(1,3)
        .map(Response::new);

    Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

    this.webTestClient
        .get()
        .uri("/api/v2/table/{input}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Response.class)
        .hasSize(3);
  }

  @Test
  public void streamingResponseTest(){
    Flux<Response> flux = Flux.range(1,3)
        .map(Response::new)
        .delayElements(Duration.ofMillis(100));

    Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

    this.webTestClient
        .get()
        .uri("/api/v2/table/{input}/stream", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
        .expectBodyList(Response.class)
        .hasSize(3);
  }



}
