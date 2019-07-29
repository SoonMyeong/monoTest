package com.soon.test.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class Webflux {


    @PostMapping(value = "/")
    public Mono<ResponseEntity<?>> receiveMessage(@RequestBody String body){

        return Mono.fromCallable(()->{
                System.out.println("receive Messsage = " + body);
            return ResponseEntity.ok("OK");
        });

    }

    @PostMapping(value = "/send")
    public void sending(){
        String test = "{Mono Test}";
        sendMessage(test);
    }


    public void sendMessage(String message){
        WebClient webClient = WebClient.builder().build();

        Mono<String> sendMesssage = webClient.post().uri("http://192.168.1.198:8088")
                                    .header("dstMRN","urn:mrn:mcl:vessel:dma:poul-lowenorn")
                                    .header("srcMRN","a")
                                    .syncBody(message).retrieve().bodyToMono(String.class);
        //push

        sendMesssage.subscribe(
                s->{
                        System.out.println("sending message..");
                    }, //onNext(T) 값이 넘어 올 때 출력
                error->{
                    System.err.println("sending error");
                }, // 에러 발생시 출력
                ()->{
                    System.out.println("send complete");
                }// 정상 종료시 출력
        );

    }


}
