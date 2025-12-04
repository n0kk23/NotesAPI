package org.rzsp.notes.notes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NoteCreateRequest(
        @JsonFormat(
                pattern = "yyyy-MM-dd"
        )
        LocalDate date,

        @Size(min = 1, max = 256)
        String description
) {
}
