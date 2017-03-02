package org.thiki.kanban.card;

import org.thiki.kanban.foundation.application.DomainOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xubt on 8/8/16.
 */
public enum CardsCodes {
    CARD_IS_NOT_EXISTS("001", "卡片未找到。"),
    CODE_IS_ALREADY_EXISTS("002", "当前看板下该编号已经存在,请使用其他编号。"),
    STAGE_WIP_REACHED_LIMIT("003", "在制品满额，不再接受卡片。"),
    PARENT_CARD_IS_NOT_FOUND("004", "父卡片不存在。"), HAS_CHILD_CARD("005", "该卡片具有从属卡片，不允许挪到到其他卡片。"),
    ACCEPTANCE_CRITERIAS_IS_NOT_COMPLETED("006", "当前卡片有验收标准尚未完成，不允许进入完成环节。"), STAGE_IS_NOT_SPECIFIED("007", "未指定卡片所属环节。");
    public static final String summaryIsRequired = "卡片概述不能为空。";
    public static final String summaryIsInvalid = "卡片概述长度超限,请保持在50个字符以内。";
    public static final String codeIsInvalid = "编号长度超长。";
    private static Map<Integer, String> sizeList = new HashMap<Integer, String>() {{
        put(1, "S");
        put(2, "M");
        put(3, "L");
        put(5, "XL");
        put(8, "XXL");
        put(9999, "!");
    }};
    private String code;
    private String message;

    CardsCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String sizeName(Integer size) {
        return sizeList.get(size);
    }

    public int code() {
        return Integer.parseInt(DomainOrder.CARD + "" + code);
    }

    public String message() {
        return message;
    }
}
