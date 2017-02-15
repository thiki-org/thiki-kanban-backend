package org.thiki.kanban.foundation.calendar;

import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.common.date.Week;

import java.util.Date;

/**
 * Created by xubt on 15/02/2017.
 */
public class CalendarService {
    public int holidaysBetweenTwoDays(String startTime, String endTime) {
        int holidaysCount = 0;
        Date endDate = DateService.instance().StringToDate(endTime);
        for (Date startDate = DateService.instance().StringToDate(startTime); startDate.before(endDate) || startDate.equals(endDate); startDate = DateService.instance().addDay(startDate, 1)) {
            Week week = DateService.instance().getWeek(startDate);
            if (week.isWeekend()) {
                holidaysCount++;
            }
        }
        return holidaysCount;
    }

    public int holidaysBetweenTwoDays(String startTime, String endTime, Date... excludeDates) {
        int holidaysCount = 0;
        Date endDate = DateService.instance().StringToDate(endTime);
        for (Date startDate = DateService.instance().StringToDate(startTime); startDate.before(endDate) || startDate.equals(endDate); startDate = DateService.instance().addDay(startDate, 1)) {
            if (isExcludeDay(startDate, excludeDates)) {
                continue;
            }
            Week week = DateService.instance().getWeek(startDate);
            if (week.isWeekend()) {
                holidaysCount++;
            }
        }
        return holidaysCount;
    }

    private boolean isExcludeDay(Date startDate, Date[] excludeDates) {
        for (Date excludeDay : excludeDates) {
            if (startDate.equals(excludeDay)) {
                return true;
            }
        }
        return false;
    }
}
