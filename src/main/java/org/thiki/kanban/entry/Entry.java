package org.thiki.kanban.entry;

import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.task.Task;

import javax.validation.constraints.NotNull;

/**
 * 任务列表
 * Created by xubitao on 04/26/16.
 */
public class Entry {

    private String id;
    /**
     * 列表标题
     */
    @NotNull(message = "Entry title should be not null.")
    @Length(min = 1, max = 50, message = "Entry title's length is between 1 to 20.")
    private String title;
    /**
     * 创建者用户Id
     */
    private Integer reporter;

    private String boardId;

    private String creationTime;
    private String modificationTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 在entry内添加一个task
     *
     * @param task
     * @return
     */
    public Task addTask(Task task) {
        // task.setEntryId(this.id);
        return task;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

}
