package org.rzsp.notes.days;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.libs.isdayoff.IsDayOff;
import org.rzsp.notes.libs.isdayoff.enums.DayType;
import org.rzsp.notes.libs.isdayoff.enums.LocalesType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * Сервис для работы с днями заметок.
 */
@Log4j2
@Service
public class DayService {
    /**
     * Хэш таблица с названиями праздников.
     */
    private static final Map<MonthDay, String> HOLIDAYS = Map.ofEntries(
            Map.entry(MonthDay.of(12, 31), "Новый год"),
            Map.entry(MonthDay.of(1, 1), "Новый год"),
            Map.entry(MonthDay.of(1, 2), "Новый год"),
            Map.entry(MonthDay.of(1, 3), "Новый год"),
            Map.entry(MonthDay.of(1, 4), "Новый год"),
            Map.entry(MonthDay.of(1, 5), "Новый год"),
            Map.entry(MonthDay.of(1, 6), "Новый год"),
            Map.entry(MonthDay.of(1, 7), "Новый год"),
            Map.entry(MonthDay.of(1, 8), "Новый год"),
            Map.entry(MonthDay.of(2, 23), "День защитника Отечества"),
            Map.entry(MonthDay.of(3, 8), "Международный женский день"),
            Map.entry(MonthDay.of(5, 1), "Праздник труда"),
            Map.entry(MonthDay.of(5, 9), "День Победы"),
            Map.entry(MonthDay.of(6, 12), "День России"),
            Map.entry(MonthDay.of(11, 4), "День народного единства")
    );

    private final IsDayOff isDayOff;

    /**
     * Конструктор, инициализирующий библиотеку {@link IsDayOff}.
     * setLocale(LocalesType.RUSSIA) - ставит российскую локализацию праздников
     * setCache(false) - отключает кэширование запросов
     */
    public DayService() {
        log.debug("Set russian locale to day service");

        this.isDayOff = IsDayOff.Builder() // почему метод с большой буквы :((((((((((
                .setLocale(LocalesType.RUSSIA)
                .setCache(false)
                .build();

        log.debug("Russian locale is set");
    }

    /**
     * Помещает информацию о выходном в ответ клиенту.
     * Объект является построенным не до конца, окончательный вид принимает в методе getNotesByDate класса NoteService
     *
     * @param date дата заметки
     * @return {@link DayNotesResponse} - не до конца построенный объект ответ клиенту
     */
    public DayNotesResponse getInformationAboutHoliday(LocalDate date) {
        log.debug("Start getting information about holiday in date {}", date.toString());

        Date dateRequest = Date.from(
                date.atStartOfDay(ZoneId.systemDefault())
                        .toInstant());

        DayType dayType = isDayOff.dayType(dateRequest);

        if (DayType.NOT_WORKING_DAY.equals(dayType)) {
            return DayNotesResponse.builder()
                    .isHoliday(true)
                    .holidayName(getHolidayName(date))
                    .build();
        }

        log.debug("Getting information about holiday is end");

        return DayNotesResponse.builder()
                .isHoliday(false)
                .build();
    }

    /**
     * Возвращает из хэш-таблицы HOLIDAYS название выходного.
     * @param date дата заметки
     * @return {@link String} - название выходного
     */
    private static String getHolidayName(LocalDate date) {
        return HOLIDAYS.getOrDefault(MonthDay.from(date), "Выходной");
    }

}
