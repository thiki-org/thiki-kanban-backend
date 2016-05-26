package org.thiki.kanban.entry;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntriesResource extends ResourceSupport {
    private List<EntryResource> entries;

    public List<EntryResource> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryResource> entries) {
        this.entries = entries;
    }

}
