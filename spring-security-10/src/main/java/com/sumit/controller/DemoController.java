package com.sumit.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
public class DemoController {

    @GetMapping(value = "/demo",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> demo(){

//        ReactiveSecurityContextHolder.getContext()
//                .map(sc-> sc.getAuthentication())
//                .subscribe(System.out::println);

        return Mono.just("dmo");
    }

    @GetMapping(value = "/hello",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> hello(){
        return Mono.just("hello");
    }
}
