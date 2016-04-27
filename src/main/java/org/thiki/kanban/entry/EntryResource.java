package org.thiki.kanban.entry;

import cn.xubitao.dolphin.foundation.resource.RestResource;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResource extends RestResource {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
