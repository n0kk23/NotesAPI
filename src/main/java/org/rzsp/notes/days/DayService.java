package org.rzsp.notes.days;

import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.libs.isdayoff.IsDayOff;
import org.rzsp.notes.libs.isdayoff.enums.DayType;
import org.rzsp.notes.libs.isdayoff.enums.LocalesType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class DayService {
    private final IsDayOff isDayOff;

    public DayService() {
        this.isDayOff = IsDayOff.Builder() // почему метод с большой буквы :((((((((((
                .setLocale(LocalesType.RUSSIA)
                .build();
    }

    public DayNotesResponse getDay(
            LocalDate date
    ) {
        Date dateRequest = Date.from(date.atStartOfDay(
                ZoneId.systemDefault()
        ).toInstant());

        DayType dayType = isDayOff.dayType(dateRequest);

        if (DayType.NOT_WORKING_DAY.equals(dayType)) {
            return DayNotesResponse.builder()
                    .isHoliday(true)
                    .build();
        }

        return DayNotesResponse.builder()
                .isHoliday(false)
                .build();
    }

}
