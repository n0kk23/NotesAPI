package org.rzsp.notes;

import org.junit.jupiter.api.Test;
import org.rzsp.notes.days.DayService;
import org.rzsp.notes.days.dto.DayNotesResponse;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DayServiceTest {

    private final DayService dayService = new DayService();

    @Test
    void testHappyNewYearDate() {
        LocalDate date = LocalDate.of(2025, 1, 1);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isTrue();
        assertThat(response.holidayName()).isEqualTo("Новый год");
    }

    @Test
    void testVictoryDayDate() {
        LocalDate date = LocalDate.of(2025, 5, 9);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isTrue();
        assertThat(response.holidayName()).isEqualTo("День Победы");
    }

    @Test
    void testDayOfRussia() {
        LocalDate date = LocalDate.of(2025, 6, 12);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isTrue();
        assertThat(response.holidayName()).isEqualTo("День России");
    }

    @Test
    void testNotWeekendDayOne() {
        LocalDate date = LocalDate.of(2025, 12, 8);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isFalse();
        assertThat(response.holidayName()).isNull();
    }

    @Test
    void testNotWeekendDayTwo() {
        LocalDate date = LocalDate.of(2025, 6, 16);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isFalse();
        assertThat(response.holidayName()).isNull();
    }

    @Test
    void testNotWeekendDayThree() {
        LocalDate date = LocalDate.of(2025, 5, 12);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isFalse();
        assertThat(response.holidayName()).isNull();
    }

    @Test
    void testDayOffWithNoHolidays() {
        LocalDate date = LocalDate.of(2025, 12, 6);

        DayNotesResponse response = dayService.getInformationAboutHoliday(date);

        assertThat(response.isHoliday()).isTrue();
        assertThat(response.holidayName()).isEqualTo("Выходной");
    }

}
