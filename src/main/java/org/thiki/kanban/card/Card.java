package org.thiki.kanban.card;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.thiki.kanban.procedure.Procedure;

import javax.validation.constraints.NotNull;

/**
 * 卡片：任何有指定负责人的有特定目标的事项，可以是用户故事，技术卡片，一次会议等等
 *
 * @author joeaniu
 */
public class Card {
    /**
     * id
     */
    private String id;
    /**
     * 简述， 出现在卡片上
     */
    @NotNull(message = CardsCodes.summaryIsRequired)
    @NotEmpty(message = CardsCodes.summaryIsRequired)
    @Length(max = 50, message = CardsCodes.summaryIsInvalid)
    private String summary;
    /**
     * 卡片内容
     */
    private String content;
    /**
     * 创建者
     */
    private String reporter;
    /**
     * 排序号
     */
    private Integer orderNumber;
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

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}
