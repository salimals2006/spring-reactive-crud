package com.web.springreactivecrud.service;

import com.web.springreactivecrud.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

  public Mono<Response> findSquare(int input){
    return Mono.fromSupplier(
        () -> input * input
    ).map(Response:: new);
  }
}
