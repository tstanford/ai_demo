package io.stanford.tim.ai_demo.services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BookmarkService {

    private final RestClient restClient;

    public BookmarkService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080").build();
    }

    public String getBookmarks() {
        String token = "eyJUb2tlblR5cGUiOiJBdXRoIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJib2IiLCJpYXQiOjE3OTQyMzc3NDUsImV4cCI6MTc5NTQ0NzM0NX0.YzKzzsmrapEBVZEg5ihIOaXetG4rq26fBoY7gD-2Hyc";
        return restClient.get()
                .uri("/api/bookmarks")
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + token)
                .retrieve()
                .body(String.class);
    }
}
