package org.thiki.kanban.assignment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentsResource extends RestResource {
    public AssignmentsResource(List<Assignment> assignmentList, String entryId, String cardId) {
        this.domainObject = assignmentList;
        JSONArray assignmentsJSONArray = new JSONArray();
        for (Assignment assignment : assignmentList) {
            AssignmentResource assignmentResource = new AssignmentResource(assignment, entryId, cardId);
            JSONObject assignmentJSON = assignmentResource.getResource();
            assignmentsJSONArray.add(assignmentJSON);
        }
        this.resourcesJSON = assignmentsJSONArray;
    }
}
