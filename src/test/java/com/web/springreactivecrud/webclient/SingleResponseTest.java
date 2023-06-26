package com.web.springreactivecrud.webclient;

import com.web.springreactivecrud.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SingleResponseTest extends BaseTest{

  @Autowired
  private WebClient webClient;

  @Test
  public void blockTest(){
    Response response = this.webClient
        .get()
        .uri("api/v2/square/{input}", 5)
        .retrieve()
        .bodyToMono(Response.class)
        .block();

    System.out.println(response);
  }


  @Test
  public void stepVerifierTest(){
    Mono<Response> responseMono = this.webClient
        .get()
        .uri("api/v2/square/{input}", 5)
        .retrieve()
        .bodyToMono(Response.class);


    StepVerifier.create(responseMono)
        .expectNextMatches(res -> res.getOutput() == 25)
        .verifyComplete();
  }

}
