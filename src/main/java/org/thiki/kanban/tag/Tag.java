package org.thiki.kanban.tag;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 11/7/16.
 */
public class Tag {

    private String id;

    @NotNull(message = TagsCodes.summaryIsRequired)
    @NotEmpty(message = TagsCodes.summaryIsRequired)
    @Length(max = 200, message = TagsCodes.summaryIsInvalid)
    private String name;
    private String color;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
