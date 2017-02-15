package org.thiki.kanban.foundation.calendar;

import org.junit.Test;
import org.thiki.kanban.foundation.common.date.DateService;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by xubt on 15/02/2017.
 */

public class CalendarServiceTest {
    @Test
    public void shouldReturnTwoDays() {
        String startTime = "2017-02-10";
        String endTime = "2017-02-13";
        int expectedHolidays = 2;

        CalendarService calendarService = new CalendarService();
        int actualHolidays = calendarService.holidaysBetweenTwoDays(startTime, endTime);

        assertEquals(expectedHolidays, actualHolidays);
    }

    @Test
    public void shouldReturnTwoDaysWhenOneOfTheDateIsWeekend() {
        String startTime = "2017-02-11";
        String endTime = "2017-02-13";
        int expectedHolidays = 2;

        CalendarService calendarService = new CalendarService();
        int actualHolidays = calendarService.holidaysBetweenTwoDays(startTime, endTime);

        assertEquals(expectedHolidays, actualHolidays);
    }

    @Test
    public void shouldReturnOneDay() {
        String startTime = "2017-02-10";
        String endTime = "2017-02-11";
        int expectedHolidays = 1;

        CalendarService calendarService = new CalendarService();
        int actualHolidays = calendarService.holidaysBetweenTwoDays(startTime, endTime);

        assertEquals(expectedHolidays, actualHolidays);
    }

    @Test
    public void shouldReturnOneDayWhenExcludingSpecifiedDays() {
        String startTime = "2017-02-11";
        String endTime = "2017-02-13";

        Date excludeDate = DateService.instance().StringToDate(startTime);
        int expectedHolidays = 1;

        CalendarService calendarService = new CalendarService();
        int actualHolidays = calendarService.holidaysBetweenTwoDays(startTime, endTime, excludeDate);

        assertEquals(expectedHolidays, actualHolidays);
    }
}
