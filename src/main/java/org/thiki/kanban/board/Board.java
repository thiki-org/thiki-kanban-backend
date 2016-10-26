package org.thiki.kanban.board;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by xubitao on 05/26/16.
 */
public class Board implements Serializable {

    private String id;
    @NotEmpty(message = BoardCodes.nameIsRequired)
    @Length(max = 20, message = BoardCodes.nameIsInvalid)
    private String name;

    private String teamId;

    private String author;

    private String owner;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String userName) {
        this.author = userName;
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isOwner(String userName) {
        return userName.equals(this.owner);
    }
}
