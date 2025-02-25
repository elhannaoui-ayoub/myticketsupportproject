package org.example;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private int user_id;

    public String getContent() {
        return  this.content;
    }

    public int getUserId() {
        return this.user_id;
    }
}
