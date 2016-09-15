package org.thiki.kanban.foundation.aspect;

/**
 * Created by xubt on 9/15/16.
 */

public class NumberFormater {
    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
            "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    public static String toCN(String chineseString) {
        String[] chineseStringArray = chineseString.split("、");
        if (chineseStringArray.length != 2 || !isNumeric(chineseStringArray[0])) {
            return chineseString;
        }

        int num = Integer.parseInt(chineseStringArray[0]);
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    continue;
                } else {
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString() + "、" + chineseStringArray[1];
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
