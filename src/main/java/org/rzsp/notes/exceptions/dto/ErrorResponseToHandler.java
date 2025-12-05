package org.rzsp.notes.exceptions.dto;

import lombok.Builder;

@Builder
public record ErrorResponseToHandler(
        int status,
        String error,
        String message
) {
}
