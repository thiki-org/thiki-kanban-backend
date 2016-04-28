package org.thiki.kanban.entry;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import cn.xubitao.dolphin.foundation.resource.RestResource;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntriesResource extends RestResource {
    private List<ResourceSupport> entries;

    public List<ResourceSupport> getEntries() {
        return entries;
    }

    public void setEntries(List<ResourceSupport> entries) {
        this.entries = entries;
    }
}
