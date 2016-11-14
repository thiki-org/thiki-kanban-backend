package org.thiki.kanban.cardTags;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 11/14/16.
 */
public enum CardTagsCodes {
    SUMMARY_IS_EMPTY("001", "标签名称不能为空。"),
    NAME_IS_ALREADY_EXIST("002", "当前看板下,该标签名称已经存在。"),
    COLOR_IS_ALREADY_EXIST("003", "当前看板下,该标签颜色已经存在。");
    public static final String nameIsRequired = "标签名称不能为空。";
    public static final String nameIsInvalid = "标签长度超限,请保持在20个字符以内。";

    public static final String colorIsRequired = "标签颜色不能为空。";
    public static final String colorIsInvalid = "标签颜色长度超限,请保持在10个字符以内。";

    private String code;
    private String message;

    CardTagsCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.CARD + "" + code);
    }

    public String message() {
        return message;
    }
}
