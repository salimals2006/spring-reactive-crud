package com.web.springreactivecrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .filter((clientRequest, exchangeFunction) -> sessionToken(clientRequest,exchangeFunction))
        //.filter(this::sessionToken)
        .build();
  }

  private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
    ClientRequest clientRequest = request
        .attribute("auth")
        .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
        .orElse(request);

    return ex.exchange(clientRequest);
  }

  private ClientRequest withBasicAuth(ClientRequest request){
    return ClientRequest.from(request)
        .headers(httpHeaders -> httpHeaders.set("username", "password"))
        .build();
  }

  private ClientRequest withOAuth(ClientRequest request){
    return ClientRequest.from(request)
        .headers(httpHeaders -> httpHeaders.setBearerAuth("some-token"))
        .build();
  }
}
