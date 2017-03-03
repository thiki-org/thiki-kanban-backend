package org.thiki.kanban.risk;

/**
 * Created by cain on 2017/2/26.
 */
public class Risk {
    private String id;
    private String cardId;
    private String startTime;
    private String endTime;  //为空代表还在阻塞状态
    private String riskReson;
    private Boolean isBlock;

//    private String stageId;
    //    private String author;
    private int type;




    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRiskReson() {
        return riskReson;
    }

    public void setRiskReson(String riskReson) {
        this.riskReson = riskReson;
    }

    public Boolean getBlock() {
        return isBlock;
    }

    public void setBlock(Boolean block) {
        isBlock = block;
    }
}
