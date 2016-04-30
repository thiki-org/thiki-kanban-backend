package org.thiki.kanban.entry;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResource extends ResourceSupport {
    
    private String title;
    private Integer reporter;
    
    public EntryResource(Entry entry) {
        if (entry == null) return;
        this.title = entry.getTitle();
        this.reporter = entry.getReporter();
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
