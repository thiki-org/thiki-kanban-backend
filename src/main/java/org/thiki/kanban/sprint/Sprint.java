package org.thiki.kanban.sprint;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by xubt on 04/02/2017.
 */
public class Sprint {
    private String id;
    @NotEmpty(message = SprintCodes.startTimeIsRequired)
    private String startTime;
    @NotEmpty(message = SprintCodes.endTimeIsRequired)
    private String endTime;
    private int status;
    private String creationTime;
    private String modificationTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
