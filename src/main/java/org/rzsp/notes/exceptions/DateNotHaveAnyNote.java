package org.rzsp.notes.exceptions;

import java.time.LocalDate;

public class DateNotHaveAnyNote extends RuntimeException {

    public DateNotHaveAnyNote(LocalDate date) {
        super("Date with this date: " + date.toString() + " doesn't have any note");
    }

}
