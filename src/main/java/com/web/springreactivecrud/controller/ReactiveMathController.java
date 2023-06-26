package com.web.springreactivecrud.controller;

import com.web.springreactivecrud.dto.Response;
import com.web.springreactivecrud.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2")
public class ReactiveMathController {

  @Autowired
  private ReactiveMathService reactiveMathService;

  @GetMapping("square/{input}")
  public Mono<Response> findSquare(@PathVariable int input){
    return this.reactiveMathService.findSquare(input);
  }
}
