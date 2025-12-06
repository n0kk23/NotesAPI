package org.rzsp.notes.exceptions;

public class NoteNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения, создает исключение в случае если заметки с указанным уникальным идентификатором несуществует.
     * @param id идентификатор заметки
     */
    public NoteNotFoundException(Long id) {
        super("Note with ID " + id + " is not found");
    }

}
