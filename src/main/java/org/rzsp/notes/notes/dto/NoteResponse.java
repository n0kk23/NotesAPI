package org.rzsp.notes.notes.dto;

public record NoteResponse(
        Long number,
        Long id,
        String description
) {
}
