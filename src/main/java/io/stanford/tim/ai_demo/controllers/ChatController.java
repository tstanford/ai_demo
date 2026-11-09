package io.stanford.tim.ai_demo.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
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

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider toolProvider) throws IOException {
        docs = new String(
                new ClassPathResource("tim.txt")
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );

        this.chatClient = builder
                .defaultTools(toolProvider)
                .build();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String message) {

        return chatClient.prompt()
                //.system(docs + "\n\nQ: What is today's date?\nA:"+ java.time.LocalDate.now())
                .user(message)
                .stream()
                .content()
                .bufferUntil(token ->
                        token.contains(".") ||
                                token.contains("!") ||
                                token.contains("?"))
                .map(tokens -> String.join("", tokens));
    }


}