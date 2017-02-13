package org.thiki.kanban.card;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.procedure.Procedure;

import javax.validation.constraints.NotNull;
import java.util.Date;

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

    private String author;

    private Integer sortNumber;
    private String creationTime;
    private String modificationTime;
    /**
     * 工序Id @see {@link Procedure#getId()}
     */
    private String procedureId;

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

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
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

    public String diff(Card originCard) {
        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(this, originCard);
        return diff.toString();
    }

    public boolean stillNoCode() {
        return getCode() == null;
    }

    public String getDeadline() {
        return DateService.instance().suffixCrop(this.deadline);
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @JsonIgnore
    public Integer getRestDays() {
        return DateService.instance().daysBetween(new Date(), this.deadline);
    }
}
