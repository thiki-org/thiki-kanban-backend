package org.thiki.kanban.stage;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 卡片环节
 * Created by xubitao on 04/26/16.
 * Modifed by winie   on  2/6/17   添加在控数量设置
 */
public class Stage {

    private String id;
    @NotEmpty(message = StageCodes.titleIsRequired)
    @NotNull(message = StageCodes.titleIsRequired)
    @Length(max = 30, message = StageCodes.titleIsInvalid)
    private String title;

    @Length(max = 100, message = StageCodes.descriptionIsInvalid)
    private String description;
    private String author;
    private String boardId;
    private Integer sortNumber;
    private Integer type;
    private Integer status;
    private String creationTime;
    private String modificationTime;
    private Integer wipLimit;

    public Integer getType() {
        return type == null ? 0 : type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status == null ? 0 : status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public boolean isInSprint() {
        return StageCodes.STAGE_TYPE_IN_PLAN.equals(this.type);
    }

    @JsonIgnore
    public boolean isInDoneStatus() {
        return StageCodes.STAGE_STATUS_DONE.equals(this.status);
    }

    @JsonIgnore
    public boolean isInProcess() {
        return StageCodes.STAGE_STATUS_DOING.equals(this.status);
    }

    @JsonIgnore
    public boolean isArchived() {
        return StageCodes.STAGE_TYPE_ARCHIVE.equals(this.type);
    }

    public Integer getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(Integer wipLimit) {
        this.wipLimit = wipLimit;
    }

    @JsonIgnore
    public boolean isReachedWipLimit(Integer currentCardsNumbers) {
        if (this.wipLimit == null || currentCardsNumbers == null) {
            return false;
        }
        return this.wipLimit.equals(currentCardsNumbers);
    }
}
