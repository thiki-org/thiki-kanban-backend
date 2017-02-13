package org.thiki.kanban.procedure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 卡片工序
 * Created by xubitao on 04/26/16.
 * Modifed by winie   on  2/6/17   添加在控数量设置
 */
public class Procedure {

    private String id;
    @NotEmpty(message = ProcedureCodes.titleIsRequired)
    @NotNull(message = ProcedureCodes.titleIsRequired)
    @Length(max = 30, message = ProcedureCodes.titleIsInvalid)
    private String title;

    @Length(max = 100, message = ProcedureCodes.descriptionIsInvalid)
    private String description;
    private String author;
    private String boardId;
    private Integer sortNumber;
    private Integer type;
    private Integer status;
    private String creationTime;
    private String modificationTime;
    private Integer wipNum;

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
        return "Procedure{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", boardId='" + boardId + '\'' +
                ", sortNumber=" + sortNumber +
                ", type=" + type +
                ", status=" + status +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", wipNum='" + wipNum + '\'' +
                '}';
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
        return ProcedureCodes.PROCEDURE_TYPE_IN_PLAN.equals(this.type);
    }

    @JsonIgnore
    public boolean isInDoneStatus() {
        return ProcedureCodes.PROCEDURE_STATUS_DONE.equals(this.status);
    }

    @JsonIgnore
    public boolean isInProcess() {
        return ProcedureCodes.PROCEDURE_STATUS_DOING.equals(this.status);
    }

    @JsonIgnore
    public boolean isArchived() {
        return ProcedureCodes.PROCEDURE_TYPE_ARCHIVE.equals(this.type);
    }

    public Integer getWipNum() {
        return wipNum;
    }

    public void setWipNum(Integer wipNum) {
        this.wipNum = wipNum;
    }
}
