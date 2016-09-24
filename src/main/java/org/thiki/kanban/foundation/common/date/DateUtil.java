package org.thiki.kanban.foundation.common.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xubt on 23/09/2016.
 */
public class DateUtil {

    public static String showTime(String date) {

        DateService dateService = new DateService();
        Date time = dateService.StringToDate(date, DateStyle.YYYY_MM_DD_HH_MM_SS);

        String convertResult = "";
        if (time == null) return convertResult;
        String format;

        long nowTimeLong = System.currentTimeMillis();

        long originTimeLong = time.getTime();
        long diffTimeLong = Math.abs(nowTimeLong - originTimeLong);

        if (diffTimeLong < 60000) {// 一分钟内
            long seconds = diffTimeLong / 1000;
            if (seconds == 0) {
                convertResult = "刚刚";
            } else {
                convertResult = seconds + "秒前";
            }
        } else if (diffTimeLong >= 60000 && diffTimeLong < 3600000) {// 一小时内
            long seconds = diffTimeLong / 60000;
            convertResult = seconds + "分钟前";
        } else if (diffTimeLong >= 3600000 && diffTimeLong < 86400000) {// 一天内
            long seconds = diffTimeLong / 3600000;
            if (dateService.getDay(date) < dateService.getDay(new Date())) {
                convertResult = "昨天";
            } else {
                convertResult = seconds + "小时前";
            }
        } else {// 日期格式
            format = "MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            convertResult = df.format(time).toString();
        }
        return convertResult;
    }
}
