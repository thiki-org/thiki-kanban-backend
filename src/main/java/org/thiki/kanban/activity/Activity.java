package org.thiki.kanban.activity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by xubt on 16/01/2017.
 */
public class Activity {
    private String id;
    private String userName;
    private String cardId;
    private String prevStageId;
    private String stageId;
    private String operationTypeCode;
    private String operationTypeName;
    private String summary;
    private String detail;
    private String creationTime;
    private String stageSnapShot;
    private String prevStageSnapShot;

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

    public String getPrevStageId() {
        return prevStageId;
    }

    public void setPrevStageId(String prevStageId) {
        this.prevStageId = prevStageId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
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

    public String getStageSnapShot() {
        return stageSnapShot;
    }

    public void setStageSnapShot(String stageSnapShot) {
        this.stageSnapShot = stageSnapShot;
    }

    public String getPrevStageSnapShot() {
        return prevStageSnapShot;
    }

    public void setPrevStageSnapShot(String prevStageSnapShot) {
        this.prevStageSnapShot = prevStageSnapShot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id != null ? id.equals(activity.id) : activity.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean isMoveOut(String stageId) {
        if (this.prevStageId == null || stageId == null) {
            return false;
        }
        return this.prevStageId.equals(stageId);
    }
}
