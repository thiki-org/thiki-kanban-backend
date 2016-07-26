package org.thiki.kanban.teamMembers;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class TeamMembersService {
    @Resource
    private TeamMembersPersistence teamMembersPersistence;

    public List<TeamMember> loadAll() {
        return teamMembersPersistence.loadAll();
    }

    public TeamMember joinTeam(String teamId, final TeamMember teamMember, String userName) {
        teamMember.setReporter(userName);
        teamMember.setTeamId(teamId);
        teamMembersPersistence.joinTeam(teamMember);
        return teamMembersPersistence.findById(teamMember.getId());
    }

    public TeamMember findById(String id) {
        return teamMembersPersistence.findById(id);
    }
}
