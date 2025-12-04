package org.rzsp.notes.notes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NoteCreateRequest(
        @NotNull
        @JsonFormat(
                pattern = "yyyy-MM-dd"
        )
        LocalDate date,

        @NotBlank
        @Size(min = 1, max = 256)
        String description
) {
}
