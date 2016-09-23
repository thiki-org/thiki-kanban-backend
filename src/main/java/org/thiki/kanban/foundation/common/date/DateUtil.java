package org.thiki.kanban.foundation.common.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xubt on 23/09/2016.
 */
public class DateUtil {

    public static String showTime(String date) {

        DateService dateService = new DateService();
        //System.out.println("当前时间是："+new Timestamp(System.currentTimeMillis()));
        Date ctime = dateService.StringToDate(date,DateStyle.YYYY_MM_DD_HH_MM_SS);

        //System.out.println("发布时间是："+df.format(ctime).toString());
        String r = "";
        if (ctime == null) return r;
        String format = "MM-dd HH:mm";

        long nowtimelong = System.currentTimeMillis();

        long ctimelong = ctime.getTime();
        long result = Math.abs(nowtimelong - ctimelong);

        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000) {// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 1702967296) {// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else {// 日期格式
            format = "MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            r = df.format(ctime).toString();
        }
        return r;
    }
}
