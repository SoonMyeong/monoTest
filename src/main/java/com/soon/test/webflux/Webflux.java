package com.soon.test.webflux;

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
        sendMessage("Test");
    }


    public void sendMessage(String message){
        WebClient webClient = WebClient.builder().build();

        Mono<String> sendMesssage = webClient.post().uri("http://localhost:9088")
                                    .syncBody(message).retrieve().bodyToMono(String.class);

        sendMesssage.subscribe(s->{
            System.out.println("sending message..");
        });


    }


}
