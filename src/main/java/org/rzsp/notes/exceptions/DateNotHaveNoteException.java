package org.rzsp.notes.exceptions;

import java.time.LocalDate;

public class DateNotHaveNoteException extends RuntimeException {

    /**
     * Конструктор исключения, создает исключение в случае если указанная дата не содержит никаких заметок.
     * @param date дата,
     */
    public DateNotHaveNoteException(LocalDate date) {
        super("Date: " + date.toString() + " doesn't have any note");
    }

}
