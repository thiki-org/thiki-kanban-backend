package org.thiki.kanban.board;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "board", key = "startsWith('#userName + boards')", allEntries = true)
    public Board create(String userName, final Board board, String projectId) {
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), projectId);
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

    @Cacheable(value = "board", key = "#projectId+'boards'")
    public List<Board> loadBoards(String projectId, String userName) {
        List<Board> projectBoards = boardsPersistence.loadBoardsByProject(projectId);
        return projectBoards;
    }

    @CacheEvict(value = "board", key = "contains(#userName)", allEntries = true)
    public Board update(String userName, String projectId, Board board) {
        Board boardToDelete = boardsPersistence.findById(board.getId());
        if (boardToDelete == null) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), projectId);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        boardsPersistence.update(board);
        return boardsPersistence.findById(board.getId());
    }

    @CacheEvict(value = "board", key = "contains('#board.id')", allEntries = true)
    public int deleteById(String boardId, String userName) {
        Board boardToDelete = boardsPersistence.findById(boardId);
        if (boardToDelete == null || !boardToDelete.isOwner(userName)) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        return boardsPersistence.deleteById(boardId, userName);
    }
}
