package org.thiki.kanban.teams.team;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Service
public class TeamsService {
    @Resource
    private TeamsPersistence teamsPersistence;
    @Resource
    private TeamMembersService teamMembersService;

    public Team create(String userName, final Team team) {

        boolean isNameConflict = teamsPersistence.isNameConflict(userName, team.getName());
        if (isNameConflict) {
            throw new BusinessException(TeamsCodes.TEAM_IS_ALREADY_EXISTS);
        }
        teamsPersistence.create(userName, team);

        teamMembersService.joinTeam(userName, team.getId());
        return teamsPersistence.findById(team.getId());
    }

    public Team findById(String id) {
        return teamsPersistence.findById(id);
    }

    public List<Team> findByUserName(String userName) {

        return teamsPersistence.findByUserName(userName);
    }

    public boolean isTeamExist(String teamId) {
        return teamsPersistence.isTeamExist(teamId);
    }

    public Team update(String teamId, Team team, String userName) {
        Team originTeam = findById(teamId);
        if (originTeam == null) {
            throw new BusinessException(TeamsCodes.TEAM_IS_NOT_EXISTS);
        }
        teamsPersistence.update(teamId, team);
        return teamsPersistence.findById(teamId);
    }
}
