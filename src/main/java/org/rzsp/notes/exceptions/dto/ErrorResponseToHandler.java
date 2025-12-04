package org.rzsp.notes.exceptions.dto;

public record ErrorResponseToHandler(
        int status,
        String error,
        String message
) {
}
