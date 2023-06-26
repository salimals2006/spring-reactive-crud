package com.web.springreactivecrud.webclient;

import com.web.springreactivecrud.dto.MultiplyRequestDto;
import com.web.springreactivecrud.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SetAuthTokenTest extends BaseTest{

  @Autowired
  private WebClient webClient;

  @Test
  public void setAuthTokenTest(){
    Mono<Response> responseMono = this.webClient
        .post()
        .uri("api/v2/multiply")
        .bodyValue(buildReqDto(5,2))
        .headers(h -> h.set("some-key", "some-val"))
        .retrieve()
        .bodyToMono(Response.class)
        .doOnNext(System.out::println);

    StepVerifier.create(responseMono)
        .expectNextCount(1)
        .verifyComplete();

  }

  private MultiplyRequestDto buildReqDto(int a, int b) {
    MultiplyRequestDto dto = new MultiplyRequestDto();
    dto.setFirst(a);
    dto.setSecond(b);
    return dto;
  }
}
