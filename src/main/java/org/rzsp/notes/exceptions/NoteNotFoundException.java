package org.rzsp.notes.exceptions;

import java.time.LocalDate;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(LocalDate date) {
        super("Notes with date " + date + " is not found");
    }

    public NoteNotFoundException(Long id) {
        super("Note with ID " + id + " is not found");
    }

}
