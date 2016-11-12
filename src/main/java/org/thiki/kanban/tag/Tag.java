package org.thiki.kanban.tag;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 11/7/16.
 */
public class Tag {

    private String id;

    @NotNull(message = TagsCodes.nameIsRequired)
    @NotEmpty(message = TagsCodes.nameIsRequired)
    @Length(max = 20, message = TagsCodes.nameIsInvalid)
    private String name;

    @NotNull(message = TagsCodes.colorIsRequired)
    @NotEmpty(message = TagsCodes.colorIsRequired)
    @Length(max = 20, message = TagsCodes.colorIsInvalid)
    private String color;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return name != null ? name.equals(tag.name) : tag.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
