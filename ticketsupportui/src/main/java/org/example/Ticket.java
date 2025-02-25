package org.example;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    public Ticket(int id, String title, String description, String priority, String category, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.comments = comments;
        this.statusHistory = statusHistory;
    }

    private int id;
    private String title;
    private String description;
    private String priority;
    private String category;
    private String status;
    private List<String> comments = new ArrayList<>();
    private List<String> statusHistory = new ArrayList<>();

    // Constructor, getters and setters...

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void addStatusChange(String statusChange) {
        statusHistory.add(statusChange);
    }

    // Getters
    public List<String> getComments() {
        return comments;
    }

    public List<String> getStatusHistory() {
        return statusHistory;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }
}

