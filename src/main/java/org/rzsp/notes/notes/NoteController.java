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
    @GetMapping("/{date}")
    public ResponseEntity<DayNotesResponse> getNotesByDate(
            @PathVariable LocalDate date
    ) {
        return ResponseEntity.ok(noteService.getNotesByDate(date));
    }

    /**
     * Post-запрос на создание заметки на указанную дату.
     *
     * @param request запрос на создание заметки
     */
    @PostMapping
    public ResponseEntity<Void> createNoteOnDate(
            @RequestBody @Valid NoteCreateRequest request
    ) {
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
        noteService.deleteAllNotesByDate(date);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
