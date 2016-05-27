package org.thiki.kanban.board;

/**
 * Created by xubitao on 05/26/16.
 */
public class Board {

    private String id;

    private String name;

    private Integer reporter;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
