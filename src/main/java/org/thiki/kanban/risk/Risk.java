package org.thiki.kanban.risk;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by cain on 2017/2/26.
 */
public class Risk {
    @NotNull(message = RiskCodes.RISK_REASON_IS_EMPTY_MESSAGE)
    @NotEmpty(message = RiskCodes.RISK_REASON_IS_EMPTY_MESSAGE)
    @Length(max = 200, message = RiskCodes.RISK_REASON_IS_INVALID)
    private String riskReason;

    private String id;
    private String cardId;
    private boolean isResolved;
    private String author;
    private int type;

    private String creationTime;
    private String modificationTime;


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

    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }

    public boolean isResolved() {
        return this.isResolved;
    }

    public void setRiskResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }
}
