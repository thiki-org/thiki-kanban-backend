package org.thiki.kanban.page;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by winie on 2017/2/6.
 */
public enum PageCodes {

    PAGE_IS_NOT_EXISTS("001", "页面未找到。");
    public static final String summaryIsRequired = "页面概述不能为空。";
    public static final String summaryIsInvalid = "页面概述长度超限,请保持在50个字符以内。";
    private String code;
    private String message;

    PageCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.PAGE + "" + code);
    }

    public String message() {
        return message;
    }
}
