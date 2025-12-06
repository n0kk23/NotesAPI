package org.rzsp.notes.notes;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST-контроллер заметок.
 */
@Log4j2
@RestController
@RequestMapping("notes")
public class NoteController {
    private final NoteService noteService;

    /**
     * Конструктор REST контроллера заметок.
     *
     * @param noteService сервис заметок
     */
    public NoteController(
            NoteService noteService
    ) {
        this.noteService = noteService;
    }

    /**
     * GET-запрос на получение всех заметок по указанной дате.
     *
     * @param date дата
     * @return {@link DayNotesResponse} - ответ в виде информации о дне и заметок на этот день
     */
    @GetMapping("/get_notes/{date}")
    public ResponseEntity<DayNotesResponse> getNotesByDate(
            @PathVariable LocalDate date
    ) {
        log.debug("GET request to get all notes by date: {}", date.toString());

        return ResponseEntity.ok(noteService.getNotesByDate(date));
    }

    /**
     * Post-запрос на создание заметки на указанную дату.
     *
     * @param request запрос на создание заметки
     */
    @PostMapping("/create_note")
    public ResponseEntity<Void> createNoteOnDate(
            @RequestBody @Valid NoteCreateRequest request
    ) {
        log.debug("POST request to create note {}", request);

        noteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete-запрос на удаление заметки по указанному индентификатору.
     *
     * @param id идентификатор заметки, которую необходимо удалить
     */
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> deleteNoteById(
            @PathVariable Long id
    ) {
        log.debug("DELETE request to delete note by ID {}", id);

        noteService.deleteNoteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * Delete-запроос на удаление заметки по указанной дате.
     *
     * @param date дата, все заметки которой необходимо удалить
     */
    @DeleteMapping("/delete/date/{date}")
    public ResponseEntity<Void> deleteAllNotesByDate(
            @PathVariable LocalDate date
    ) {
        log.debug("DELETE request to delete note by date {}", date.toString());

        noteService.deleteAllNotesByDate(date);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
