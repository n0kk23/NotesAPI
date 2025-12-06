package org.rzsp.notes.exceptions;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Note with ID " + id + " is not found");
    }

}
