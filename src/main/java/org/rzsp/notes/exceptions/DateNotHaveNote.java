package org.rzsp.notes.exceptions;

import java.time.LocalDate;

public class DateNotHaveNote extends RuntimeException {

    /**
     * Конструктор исключения, создает исключение в случае если указанная дата не содержит никаких заметок.
     * @param date дата,
     */
    public DateNotHaveNote(LocalDate date) {
        super("Date with this date: " + date.toString() + " doesn't have any note");
    }

}
