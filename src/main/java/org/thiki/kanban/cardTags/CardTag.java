package org.thiki.kanban.cardTags;

import com.alibaba.fastjson.JSON;

/**
 * Created by xubt on 11/14/16.
 */
public class CardTag {

    private String id;
    private String tagId;

    private String name;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardTag cardTag = (CardTag) o;

        return tagId != null ? tagId.equals(cardTag.tagId) : cardTag.tagId == null;

    }

    @Override
    public int hashCode() {
        return tagId != null ? tagId.hashCode() : 0;
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
    public String toString() {
        return JSON.toJSONString(this);
    }
}
