package org.thiki.kanban.procedure;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
public class ProceduresResource extends RestResource {

    public ProceduresResource(List<Procedure> procedureList, String boardId) {
        this.domainObject = procedureList;
        JSONArray proceduresJSONArray = new JSONArray();
        for (Procedure procedure : procedureList) {
            ProcedureResource procedureResource = new ProcedureResource(procedure, boardId);
            JSONObject procedureJSON = procedureResource.getResource();
            proceduresJSONArray.add(procedureJSON);
        }
        this.resourcesJSON = proceduresJSONArray;
    }
}
