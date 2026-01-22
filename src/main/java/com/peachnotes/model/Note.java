package com.peachnotes.model;

import java.time.Instant;

public class Note {
    private long id;
    private String text;
    private Instant createdAt;

    public Note() {}

    public Note(long id, String text, Instant createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
