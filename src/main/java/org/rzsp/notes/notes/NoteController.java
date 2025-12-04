package org.rzsp.notes.notes;

import jakarta.validation.Valid;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(
            NoteService noteService
    ) {
        this.noteService = noteService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<DayNotesResponse> getNotesByDate(
            @PathVariable LocalDate date
    ) {
        return ResponseEntity.ok(noteService.getNotesByDate(date));
    }

    @PostMapping
    public ResponseEntity<Void> createNoteOnDate(
            @RequestBody @Valid NoteCreateRequest request
    ) {
        noteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> deleteNoteById(
            @PathVariable Long id
    ) {
        noteService.deleteNoteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/delete/date/{date}")
    public ResponseEntity<Void> deleteAllNotesByDate(
            @PathVariable LocalDate date
    ) {
        noteService.deleteAllNotesByDate(date);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
