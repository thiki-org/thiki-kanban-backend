package org.thiki.kanban.risk;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by wisdom on 3/12/17.
 */
public enum RiskCodes {

    RISK_REASON_IS_EMPTY("001", "风险的原因不能为空");

    public final static String RISK_REASON_IS_EMPTY_MESSAGE = "风险的原因不能为空";
    public static final String RISK_REASON_IS_INVALID = "风险的理由限制早在200个字符";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = DomainOrder.RISK + "" + code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    RiskCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }


    private String code;
    private String message;
}
