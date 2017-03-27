package org.thiki.kanban.statistics.burnDownChart;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.NotNull;


public class BurnDownChart {
    private String id;
    @NotNull
    private String boardId;
    @NotNull
    private String sprintId;
    @NotNull
    private String sprintAnalyseTime;
    private Integer storyPoint;
    private Integer storyDonePoint;
    private String creationTime;
    private String modificationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintAnalyseTime() {
        return sprintAnalyseTime;
    }

    public void setSprintAnalyseTime(String sprintAnalyseTime) {
        this.sprintAnalyseTime = sprintAnalyseTime;
    }

    public Integer getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(Integer storyPoint) {
        this.storyPoint = storyPoint;
    }

    public Integer getStoryDonePoint() {
        return storyDonePoint;
    }

    public void setStoryDonePoint(Integer storyDonePoint) {
        this.storyDonePoint = storyDonePoint;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
