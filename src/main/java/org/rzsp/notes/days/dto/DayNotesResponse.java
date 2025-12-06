package org.rzsp.notes.days.dto;

import lombok.Builder;
import org.rzsp.notes.notes.dto.NoteResponse;

import java.util.List;

/**
 * Класс преобразователь в ответ клиенту в виде дня и списка заметок.
 *
 * @param isHoliday является ли день выходным
 * @param holidayName если является, то какой праздник, если не праздник - выходной
 * @param notes список заметок, привязанных к данной дате
 */
@Builder
public record DayNotesResponse(
        boolean isHoliday,
        String holidayName,
        List<NoteResponse> notes
) {
}
