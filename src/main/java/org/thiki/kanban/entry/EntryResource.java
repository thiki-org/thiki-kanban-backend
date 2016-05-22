package org.thiki.kanban.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResource extends ResourceSupport {
    @JsonProperty
    private String id;
    private String title;
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


    public EntryResource(Entry entry) {
        if (entry == null) return;
        this.id = entry.getId();
        this.title = entry.getTitle();
        this.reporter = entry.getReporter();
        this.creationTime = entry.getCreationTime();
        this.modificationTime = entry.getModificationTime();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
        this.reporter = reporter;
    }

}
