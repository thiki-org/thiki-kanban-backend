package org.thiki.kanban.board.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardCodes;
import org.thiki.kanban.board.BoardResource;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.foundation.exception.AuthenticationException;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.security.authentication.AuthenticationProvider;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;

import javax.annotation.Resource;

/**
 * Created by xubt on 24/10/2016.
 */
@Service("boardAuthenticationProvider")
public class BoardAuthenticationProvider extends AuthenticationProvider {
    @Resource
    private BoardsService boardsService;
    @Resource
    private TeamMembersService teamMembersService;

    public String getPathTemplate() {
        return BoardResource.URL_TEMPLATE;
    }

    @Override
    public boolean authenticate(String url, String method, String userName) {
        String boardId = (String) getPathValues(url).get("boardId");
        Board board = boardsService.findById(boardId);
        if (board == null) {
            throw new BusinessException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        if (board.getOwner().equals(userName)) {
            return true;
        }
        boolean isTeamMember = teamMembersService.isMember(board.getTeamId(), userName);
        if (!isTeamMember) {
            throw new AuthenticationException(BoardCodes.FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM);
        }
        return true;
    }

    @Override
    public boolean authGet() {
        String boardId = (String) getPathValues(this.hrefValue).get("boardId");
        Board board = boardsService.findById(boardId);
        if (board == null) {
            throw new BusinessException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        return board.isOwner(userName) || teamMembersService.isMember(board.getTeamId(), userName);
    }

    @Override
    public boolean authPost() {
        return false;
    }

    @Override
    public boolean authDelete() {
        return boardsService.isBoardOwner(pathParams.get("boardId"), userName);
    }

    @Override
    public boolean authPut() {
        return boardsService.isBoardOwner(pathParams.get("boardId"), userName);
    }
}
