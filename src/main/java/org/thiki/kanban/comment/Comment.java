package org.thiki.kanban.comment;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.thiki.kanban.foundation.common.date.DateUtil;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 10/31/16.
 */
public class Comment {

    private String id;

    @NotNull(message = CommentCodes.summaryIsRequired)
    @NotEmpty(message = CommentCodes.summaryIsRequired)
    @Length(max = 200, message = CommentCodes.summaryIsInvalid)
    private String summary;
    private Boolean finished;
    private String author;
    private Integer sortNumber;
    private String creationTime;
    private String modificationTime;
    private String publishTime;

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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getPublishTime() {
        return DateUtil.showTime(creationTime);
    }
}
