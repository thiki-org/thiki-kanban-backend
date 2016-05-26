package org.thiki.kanban.entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntriesResource extends RestResource {

    public EntriesResource(List<Entry> entryList, String boardId) {
        this.domainObject = entryList;
        JSONArray entriesJSONArray = new JSONArray();
        for (Entry entry : entryList) {
            EntryResource entryResource = new EntryResource(entry, boardId);
            JSONObject entryJSON = entryResource.getResource();
            entriesJSONArray.add(entryJSON);
        }
        this.resourcesJSON = entriesJSONArray;
    }
}
