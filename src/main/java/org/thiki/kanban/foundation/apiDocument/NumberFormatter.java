package org.thiki.kanban.foundation.apiDocument;

/**
 * Created by xubt on 9/15/16.
 */

public class NumberFormatter {

    private String original;
    private String desc = "";
    private String order;

    private static final char[] cnNumbers = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    private static final char[] series = {' ', '十', '百', '仟', '万', '拾', '百', '仟', '亿'};

    private boolean isOriginalValid = true;

    public NumberFormatter(String original) {
        this.original = original;
        String[] chineseStringArray = original.split(",");
        if (chineseStringArray.length != 2 || !isNumeric(chineseStringArray[0])) {
            isOriginalValid = false;
            return;
        }
        this.order = chineseStringArray[0];
        this.desc = chineseStringArray[1];
    }

    /**
     * 取得大写形式的字符串
     *
     * @return
     */
    public String getCnString() {
        if (!isOriginalValid) {
            return this.original;
        }
        // 因为是累加所以用StringBuffer
        StringBuffer sb = new StringBuffer();

        // 整数部分处理
        for (int i = 0; i < order.length(); i++) {
            int number = getNumber(order.charAt(i));
            if (Integer.parseInt(order) > 100 || Integer.parseInt(order) < 10) {
                sb.append(cnNumbers[number]);
            }
            if (Integer.parseInt(order) >= 10) {
                sb.append(series[order.length() - 1 - i]);
            }
        }

        // 返回拼接好的字符串
        return sb.toString() + "、" + this.desc;
    }

    /**
     * 将字符形式的数字转化为整形数字
     * 因为所有实例都要用到所以用静态修饰
     *
     * @param c
     * @return
     */
    private static int getNumber(char c) {
        String str = String.valueOf(c);
        return Integer.parseInt(str);
    }

    private static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
