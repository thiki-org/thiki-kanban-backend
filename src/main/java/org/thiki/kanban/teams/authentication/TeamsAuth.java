package org.thiki.kanban.teams.authentication;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.teams.teamMembers.TeamMembersCodes;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by xubt on 21/10/2016.
 */
@Service("teamsAuth")
public class TeamsAuth implements Authentication {
    private String userName;
    private String hrefValue;

    @Resource
    private BoardsService boardsService;
    @Resource
    private TeamMembersService teamMembersService;

    @Override
    public boolean authenticate(String url, String method, String userName) {
        UriTemplate uriTemplate = new UriTemplate("/boards/{boardId}/procedures");
        Map values = uriTemplate.match(url);
        String boardId = (String) values.get("boardId");
        Board board = boardsService.findById(boardId);
        boolean isTeamMember = teamMembersService.isMember(board.getTeamId(), userName);
        if (!isTeamMember) {
            throw new BusinessException(TeamMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_TEAM);
        }
        return true;
    }

    @Override
    public void config(String hrefValue, String userName) {
        this.hrefValue = hrefValue;
        this.userName = userName;
    }

    @Override
    public boolean authGet() {
        return false;
    }

    @Override
    public boolean authPost() {
        return false;
    }

    @Override
    public boolean authDelete() {
        return false;
    }

    @Override
    public boolean authPut() {
        return false;
    }

    @Override
    public String getPathTemplate() {
        return null;
    }

    @Override
    public boolean matchPath(String path) {
        return true;
        // TODO: 22/10/2016  具体的匹配规则
    }
}
