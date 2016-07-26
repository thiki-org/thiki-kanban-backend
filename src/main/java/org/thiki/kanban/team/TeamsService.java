package org.thiki.kanban.team;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Service
public class TeamsService {
    @Resource
    private TeamsPersistence teamsPersistence;

    public Team create(String reporter,final Team team){
        team.setReporter(reporter);
        teamsPersistence.create(team);
        return teamsPersistence.findById(team.getId());
    }
    public Team findById(String id) {
        return teamsPersistence.findById(id);
    }

    public int deleteById(String id) {
        Team teamToDelete = teamsPersistence.findById(id);
        if (teamToDelete == null) {
            throw new ResourceNotFoundException("team[" + id + "] is not found.");
        }
        return teamsPersistence.deleteById(id);
    }
    public Team update(Team team) {
        teamsPersistence.update(team);
        return teamsPersistence.findById(team.getId());
    }
}
