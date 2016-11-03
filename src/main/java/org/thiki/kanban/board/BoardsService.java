package org.thiki.kanban.board;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;
import org.thiki.kanban.teams.team.Team;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class BoardsService {

    @Resource
    private BoardsPersistence boardsPersistence;
    @Resource
    private TeamMembersService teamMembersService;

    public Board create(String userName, final Board board) {
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), userName);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        board.setAuthor(userName);
        boardsPersistence.create(board);
        return boardsPersistence.findById(board.getId());
    }

    public Board findById(String id) {
        return boardsPersistence.findById(id);
    }

    public List<Board> loadBoards(String userName) {
        List<Board> personalBoards = boardsPersistence.findPersonalBoards(userName);
        List<Board> teamsBoards = loadTeamsBoards(userName);

        List<Board> boards = new ArrayList<>();
        boards.addAll(personalBoards);
        boards.addAll(teamsBoards);
        return boards;
    }

    private List<Board> loadTeamsBoards(String userName) {
        List<Board> teamsBoards = new ArrayList<>();
        List<Team> teams = teamMembersService.loadTeamsByUserName(userName);
        for (Team team : teams) {
            List<Board> teamBoards = boardsPersistence.findTeamsBoards(team.getId());
            teamsBoards.addAll(teamBoards);
        }
        return teamsBoards;
    }

    public Board update(String userName, Board board) {
        Board boardToDelete = boardsPersistence.findById(board.getId());
        if (boardToDelete == null) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), userName);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        boardsPersistence.update(board);
        return boardsPersistence.findById(board.getId());
    }

    public int deleteById(String id) {
        Board boardToDelete = boardsPersistence.findById(id);
        if (boardToDelete == null) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        return boardsPersistence.deleteById(id);
    }

    public boolean isBoardOwner(String boardId, String userName) {
        Board board = findById(boardId);
        if (board == null) {
            throw new BusinessException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        if (board.isOwner(userName)) {
            return true;
        }
        boolean isTeamMember = teamMembersService.isMember(board.getTeamId(), userName);
        return isTeamMember && board.getOwner().equals(userName);
    }
}
