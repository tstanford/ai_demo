package io.stanford.tim.ai_demo.tools;

import io.stanford.tim.ai_demo.services.BookmarkService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class BookmarkTools {

    private final BookmarkService bookmarkService;

    public BookmarkTools(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Tool(description = "Retrieve all bookmarks from the bookmark service")
    public String getBookmarks() {

        return bookmarkService.getBookmarks();
    }
}