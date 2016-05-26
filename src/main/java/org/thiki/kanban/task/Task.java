package org.thiki.kanban.task;

import org.thiki.kanban.entry.Entry;

/**
 * 任务：任何有指定负责人的有特定目标的事项，可以是用户故事，技术任务，一次会议等等
 *
 * @author joeaniu
 */
public class Task {
    /**
     * id
     */
    private String id;
    /**
     * 简述， 出现在卡片上
     */
    private String summary;
    /**
     * 任务内容
     */
    private String content;
    /**
     * 创建者
     */
    private Integer reporter;
    /**
     * 排序号
     */
    private Integer orderNumber;
    private String creationTime;
    private String modificationTime;
    /**
     * 任务列表Id @see {@link Entry#getId()}
     */
    private String entryId;

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

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
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
