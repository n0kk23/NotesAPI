package org.rzsp.notes.days.dto;

import lombok.Builder;
import org.rzsp.notes.notes.dto.NoteResponse;

import java.util.List;

@Builder
public record DayNotesResponse(
        boolean isHoliday,
        String holidayName,
        List<NoteResponse> notes
) {
}
