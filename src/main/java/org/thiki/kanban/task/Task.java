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
    private Integer id;
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
     * 负责人
     */
    private Integer assignee;
    /**
     * 任务列表Id @see {@link Entry#getId()}
     */
    private Integer entryId;

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
        this.reporter = reporter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

}
