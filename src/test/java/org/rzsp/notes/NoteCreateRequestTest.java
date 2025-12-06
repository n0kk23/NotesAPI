package org.rzsp.notes;

import org.junit.jupiter.api.Test;
import org.rzsp.notes.notes.dto.NoteCreateRequest;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NoteCreateRequestTest {

    @Test
    void testValidNoteCreateRequestTest() {
        LocalDate date = LocalDate.of(2025, 12, 6);
        String description = "something";

        NoteCreateRequest test = new NoteCreateRequest(date, description);

        assertEquals(LocalDate.of(2025, 12, 6), test.date());
        assertEquals("something", test.description());
    }

    @Test
    void testNotValidDateInCreateRequestTest() {
        String description = "something";

        assertThrows(
            DateTimeException.class, () -> {
            LocalDate date = LocalDate.of(2025, 13, 6);
            new NoteCreateRequest(date, description);
        });
    }

}
