package org.rzsp.notes.isdayoff;

import org.rzsp.notes.isdayoff.enums.DayType;

import java.util.Date;

public class IsDayOffDateType {
    /**
     * Дата
     */
    private Date date;
    /**
     * Тип дня
     * @see org.rzsp.notes.isdayoff.enums.DayType
     */
    private DayType dayType;

    public IsDayOffDateType(Date date, DayType dayType) {
        this.date = date;
        this.dayType = dayType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Date getDate() {
        return date;
    }

    public DayType getDayType() {
        return dayType;
    }
}