package org.thiki.kanban.card;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.stage.Stage;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * 卡片：任何有指定负责人的有特定目标的事项，可以是用户故事，技术卡片，一次会议等等
 *
 * @author joeaniu
 */
public class Card {
    private String id;
    @NotNull(message = CardsCodes.summaryIsRequired)
    @NotEmpty(message = CardsCodes.summaryIsRequired)
    @Length(max = 200, message = CardsCodes.summaryIsInvalid)
    private String summary;
    @Length(max = 50, message = CardsCodes.codeIsInvalid)
    private String code;
    private String content;
    private String deadline;
    private Integer size;
    private String sizeName;
    private String author;

    private Integer sortNumber;
    private String creationTime;
    private String modificationTime;
    /**
     * 环节Id @see {@link Stage#getId()}
     */
    private String stageId;
    private double elapsedDays;
    private String parentId;

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

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean stillNoCode() {
        return getCode() == null;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @JsonIgnore
    public Integer getRestDays() {
        Integer restDays = DateService.instance().daysBetween(new Date(), this.deadline);
        if (restDays != null) {
            restDays++;
        }
        return restDays;
    }

    @JsonIgnore
    public boolean isMoveToOtherStage(Card originCard) {
        return !originCard.stageId.equals(this.stageId);
    }

    public double getElapsedDays() {
        return elapsedDays;
    }

    public void setElapsedDays(double elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSizeName() {
        return CardsCodes.sizeName(this.size);
    }

    public boolean moveToParent(Card originCard) {
        return !Objects.equals(originCard.getParentId(), this.parentId) && !Objects.equals(this.parentId, "");
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
