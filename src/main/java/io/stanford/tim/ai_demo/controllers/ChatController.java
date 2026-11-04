package io.stanford.tim.ai_demo.controllers;

import io.stanford.tim.ai_demo.dtos.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {

        return chatClient.prompt()
                .user(request.message())
                .call()
                .content();
    }
}