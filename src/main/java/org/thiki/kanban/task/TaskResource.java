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

    private Integer orderNumber;

    private String creationTime;
    private String modificationTime;

    public String getCreationTime() {
        return creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }


    public TaskResource(Task task) {
        if (task == null) {
            return;
        }
        this.summary = task.getSummary();
        this.content = task.getContent();
        this.assignee = task.getOrderNumber();
        this.reporter = task.getReporter();
        this.entryId = task.getEntryId();
        this.orderNumber = task.getOrderNumber();
        this.creationTime = task.getCreationTime();
        this.modificationTime = task.getModificationTime();
    }

    public Integer getReporter() {
        return reporter;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public Integer getAssignee() {
        return assignee;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

}
