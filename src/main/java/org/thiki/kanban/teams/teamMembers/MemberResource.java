package org.thiki.kanban.teams.teamMembers;

import org.thiki.kanban.foundation.common.RestResource;

/**
 * Created by 濤 on 7/26/16.
 */
public class MemberResource extends RestResource {
    public MemberResource(Member member) {
        this.domainObject = member;
    }
}
