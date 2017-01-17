package org.thiki.kanban.activity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by xubt on 16/01/2017.
 */
public class Activity {
    private String id;
    private String userName;
    private String cardId;
    private String prevProcedureId;
    private String procedureId;
    private String operationTypeCode;
    private String operationTypeName;
    private String summary;
    private String detail;
    private String creationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperationTypeCode() {
        return operationTypeCode;
    }

    public void setOperationTypeCode(String operationTypeCode) {
        this.operationTypeCode = operationTypeCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrevProcedureId() {
        return prevProcedureId;
    }

    public void setPrevProcedureId(String prevProcedureId) {
        this.prevProcedureId = prevProcedureId;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOperationTypeName() {
        return operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        this.operationTypeName = operationTypeName;
    }
}
