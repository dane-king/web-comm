package com.daneking.websocket.details;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LookupService {
    private final WebClient webClient;

    public LookupService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://jsonplaceholder.typicode.com/").build();
    }

    public Mono<Details> getResults(String id) {
        return this.webClient.get().uri("/posts/{id}", id).retrieve().bodyToMono(Details.class);
    }

}
