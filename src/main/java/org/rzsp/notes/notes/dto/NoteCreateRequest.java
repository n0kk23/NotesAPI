package org.rzsp.notes.notes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Заметка запрос созданная клиентом
 * @param date дата, к которой должна быть привязана заметка
 * @param description сообщение заметки
 */
public record NoteCreateRequest(
        @NotNull
        @JsonFormat(
                pattern = "yyyy-MM-dd"
        )
        LocalDate date,

        @NotBlank
        @Size(min = 1, max = 1024)
        String description
) {
}
