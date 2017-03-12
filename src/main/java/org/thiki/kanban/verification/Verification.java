package org.thiki.kanban.verification;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by skytao on 03/06/17.
 */
public class Verification {

    private String id;

    @NotNull(message = VerificationCodes.IS_PASSED_NOT_VALID)
    private Integer isPassed;
    @Length(min = 1, max = 100, message = VerificationCodes.REMARK_IS_NOT_VALID)
    private String remark;
    @NotNull(message = VerificationCodes.ACCEPTANCE_CRITERIA_ID__IS_NOT_VALID)
    @NotEmpty(message = VerificationCodes.ACCEPTANCE_CRITERIA_ID__IS_NOT_VALID)
    private String acceptanceCriteriaId;
    private String author;
    private String creationTime;
    private String modificationTime;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAcceptanceCriteriaId() {
        return acceptanceCriteriaId;
    }

    public void setAcceptanceCriteriaId(String acceptanceCriteriaId) {
        this.acceptanceCriteriaId = acceptanceCriteriaId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @JsonIgnore
    public boolean isPassed() {
        return VerificationCodes.IS_PASSED.equals(this.isPassed);
    }
}
