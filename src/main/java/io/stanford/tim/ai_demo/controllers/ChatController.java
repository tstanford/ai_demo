package io.stanford.tim.ai_demo.controllers;

import io.stanford.tim.ai_demo.dtos.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final String docs;

    public ChatController(ChatClient.Builder builder) throws IOException {

        docs = new String(
                new ClassPathResource("tim.txt")
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );

        this.chatClient = builder.build();
    }

    @PostMapping(value = "",
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> stream(@RequestBody ChatRequest request) {

        return chatClient.prompt()
                .system(docs + "\n\nQ: What is todays date?\nA:"+ java.time.LocalDate.now())
                .user(request.message())
                .stream()
                .content();
    }


}