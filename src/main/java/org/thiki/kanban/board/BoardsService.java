package org.thiki.kanban.board;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class BoardsService {

    @Resource
    private BoardsPersistence boardsPersistence;

    public Board create(String userName, final Board board) {
        boolean isExists = boardsPersistence.unique(board.getName(), userName);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        board.setReporter(userName);
        boardsPersistence.create(board);
        return boardsPersistence.findById(board.getId());
    }

    public Board findById(String id) {
        return boardsPersistence.findById(id);
    }

    public List<Board> loadByUserName(String userName) {
        return boardsPersistence.loadByUserName(userName);
    }

    public Board update(Board board) {
        boardsPersistence.update(board);
        return boardsPersistence.findById(board.getId());
    }

    public int deleteById(String id) {
        Board boardToDelete = boardsPersistence.findById(id);
        if (boardToDelete == null) {
            throw new ResourceNotFoundException("board[" + id + "] is not found.");
        }
        return boardsPersistence.deleteById(id);
    }

    public List<Board> findByUserId(String userId) {
        return boardsPersistence.findByUserId(userId);
    }

    public List<Board> findByTeamId(String teamId) {
        return boardsPersistence.findByTeamId(teamId);
    }

}
