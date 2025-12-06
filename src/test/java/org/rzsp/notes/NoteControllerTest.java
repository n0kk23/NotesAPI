package org.rzsp.notes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.notes.NoteController;
import org.rzsp.notes.notes.NoteService;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    @Mock
    private NoteService service;

    @InjectMocks
    private NoteController controller;

    @Test
    void validGetNotesByDate() {
        DayNotesResponse response = mock(DayNotesResponse.class);
        when(service.getNotesByDate(
                LocalDate.of(2025, 12, 6))
        ).thenReturn(response);

        ResponseEntity<DayNotesResponse> result = controller.getNotesByDate(LocalDate.of(2025, 12, 6));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
        verify(service, times(1)).getNotesByDate(LocalDate.of(2025, 12, 6));
    }

    @Test
    void validCreateNoteRequest() {
        NoteCreateRequest request = mock(NoteCreateRequest.class);
        doNothing().when(service).createNote(any());

        ResponseEntity<Void> result = controller.createNoteOnDate(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(service, times(1)).createNote(request);
    }

    @Test
    void deleteNoteByWrongId() {
        Long id = 0L;
        doNothing().when(service).deleteNoteById(id);

        ResponseEntity<Void> result = controller.deleteNoteById(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service, times(1)).deleteNoteById(id);
    }

    @Test
    void deleteNotesByWrongDate() {
        doNothing().when(service).deleteAllNotesByDate(LocalDate.of(2025, 12, 6));

        ResponseEntity<Void> result = controller.deleteAllNotesByDate(LocalDate.of(2025, 12, 6));

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service, times(1)).deleteAllNotesByDate(LocalDate.of(2025, 12, 6));
    }

}
