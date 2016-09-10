package org.thiki.kanban.teamMembers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubt on 9/10/16.
 */
public class MembersResource extends RestResource {
    public MembersResource(List<Member> members) {
        this.domainObject = members;
        JSONArray membersJSONArray = new JSONArray();
        for (Member member : members) {
            MemberResource memberResource = new MemberResource(member);
            JSONObject memberJSON = memberResource.getResource();
            membersJSONArray.add(memberJSON);
        }
        this.resourcesJSON = membersJSONArray;
    }
}
