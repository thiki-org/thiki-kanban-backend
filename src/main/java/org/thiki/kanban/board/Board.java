package org.thiki.kanban.board;

import java.io.Serializable;

/**
 * Created by xubitao on 05/26/16.
 */
public class Board implements Serializable {

    private String id;

    private String name;

    private String reporter;

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

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String userName) {
        this.reporter = userName;
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
