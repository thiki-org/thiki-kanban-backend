package org.thiki.kanban.acceptanceCriteria;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 10/17/16.
 */
public class AcceptCriteria {

    private String id;

    @NotNull(message = AcceptanceCriteriaCodes.summaryIsRequired)
    @NotEmpty(message = AcceptanceCriteriaCodes.summaryIsRequired)
    @Length(max = 100, message = AcceptanceCriteriaCodes.summaryIsInvalid)
    private String summary;
    private Integer isFinished;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
}
