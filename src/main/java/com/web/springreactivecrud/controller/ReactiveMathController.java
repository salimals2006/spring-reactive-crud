package com.web.springreactivecrud.controller;

import com.web.springreactivecrud.dto.MultiplyRequestDto;
import com.web.springreactivecrud.dto.Response;
import com.web.springreactivecrud.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("api/v2")
public class ReactiveMathController {

  @Autowired
  private ReactiveMathService reactiveMathService;

  @GetMapping("square/{input}")
  public Mono<Response> findSquare(@PathVariable int input){
    return this.reactiveMathService.findSquare(input);
  }

  @GetMapping("table/{input}")
  public Flux<Response> multiplicationTable(@PathVariable int input) {
    return this.reactiveMathService.multiplicationTable(input);
  }

  //Exposing stream api
  @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Response> multiplicationTableStream(@PathVariable int input) {
    return this.reactiveMathService.multiplicationTable(input);
  }

  @PostMapping("multiply")
  public Mono<Response> multiply(
      @RequestBody Mono<MultiplyRequestDto> requestDtoMono,
      @RequestHeader Map<String, String> headers) {
    System.out.println("Headers : " + headers);
    return this.reactiveMathService.multiply(requestDtoMono);
  }


}
