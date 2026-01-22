package com.peachnotes.controller;

import com.peachnotes.model.Note;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(
        origins = {
                "http://localhost:5500",
                "http://localhost:5173",
                "https://Angie-Torres-M.github.io"
        }
)
public class NotesController {

    private final List<Note> notes = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(1);

    @GetMapping
    public List<Note> getAll() {
        notes.sort(Comparator.comparing(Note::getCreatedAt).reversed());
        return notes;
    }

    public static class CreateNoteRequest {
        public String text;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@RequestBody CreateNoteRequest body) {
        if (body == null || body.text == null || body.text.trim().isEmpty()) {
            throw new IllegalArgumentException("text is required");
        }

        String text = body.text.trim();
        if (text.length() > 200) {
            throw new IllegalArgumentException("text must be <= 200 characters");
        }

        Note note = new Note(seq.getAndIncrement(), text, Instant.now());
        notes.add(note);
        return note;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean removed = notes.removeIf(n -> n.getId() == id);
        if (!removed) {
            throw new NoteNotFoundException("Note not found: " + id);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class NoteNotFoundException extends RuntimeException {
        public NoteNotFoundException(String message) { super(message); }
    }
}
