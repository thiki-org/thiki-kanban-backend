package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsPersistence;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;


import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;


@Service
public class PagesService {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaService.class);
    @Resource
    private PagesPersistence pagesPersistence;

    @Resource
    private BoardsPersistence boardsPersistence;

    @CacheEvict(value = "page", key = "contains('#{boardId}')", allEntries = true)
    public Page create(String userName, String boardId, Page page) {
        logger.info("Creating new card:{},boardID:{}", page, boardId);
        Board board = boardsPersistence.findById(boardId);
        if (board == null) {
            throw new ResourceNotFoundException("board[" + boardId + "] is not found.");
        }
        pagesPersistence.create(userName, page);
        Page savedPage = pagesPersistence.findById(page.getId());
        logger.info("Created page:{}", savedPage);
        return savedPage;
    }

    @CacheEvict(value = "page", key = "contains('#boardId')", allEntries = true)
    public Page modify(String pageId, Page page,  String boardId, String userName) {
        logger.info("modify page:{}", page);
        pagesPersistence.modify(pageId, page);
        Page savedPage = pagesPersistence.findById(pageId);
        logger.info("Modified page:{}", savedPage);
        return savedPage;
    }

    @CacheEvict(value = "page", key = "contains('#pageId')", allEntries = true)
    public int deleteById(String pageId) {
        Page foundPage = pagesPersistence.findById(pageId);
        if (foundPage == null) {
            throw new BusinessException(PageCodes.PAGE_IS_NOT_EXISTS);
        }
        logger.info("Deleting page.pageId:{}", pageId);
        return pagesPersistence.deleteById(pageId);
    }
    @Cacheable(value = "page", key = "'pages'+#boardId")
    public List<Page> findByBoardId(String boardId) {
        logger.info("Loading pages by boardId:{}", boardId);
        Board board = boardsPersistence.findById(boardId);
        if (board == null) {
            throw new ResourceNotFoundException(MessageFormat.format("board[{0}] is not found.", boardId));
        }
        List<Page> pages = pagesPersistence.findByBoardId(boardId);
        logger.info("The pages belongs from the board {} are {}", boardId, pages);
        return pages;
    }

}
