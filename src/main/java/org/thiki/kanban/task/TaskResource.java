package org.thiki.kanban.task;

import org.springframework.hateoas.ResourceSupport;

/**
 * 任务的资源DTO
 *
 * @author joeaniu
 */
public class TaskResource extends ResourceSupport {
    /**
     * 简述， 出现在卡片上
     */
    private String summary;
    /**
     * 任务内容
     */
    private String content;
    /**
     * 负责人
     */
    private Integer assignee;
    /**
     * 创建者
     */
    private Integer reporter;
    /**
     * 任务组Id
     */
    private String entryId;

    public TaskResource(Task task) {
        this.summary = task.getSummary();
        this.content = task.getContent();
        this.assignee = task.getAssignee();
        this.reporter = task.getReporter();
        this.entryId = task.getEntryId();
    }

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
        this.reporter = reporter;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
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
