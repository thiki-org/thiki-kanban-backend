package org.thiki.kanban.board;

import org.springframework.stereotype.Service;
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

    public Board create(Integer reporterUserId, final Board board) {
        board.setReporter(reporterUserId);
        boardsPersistence.create(board);
        return boardsPersistence.findById(board.getId());
    }

    public Board findById(String id) {
        return boardsPersistence.findById(id);
    }

    public List<Board> loadAll() {
        return boardsPersistence.loadAll();
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
}
