package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;


@Service
public class PagesService {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaService.class);
    @Resource
    private PagesPersistence pagesPersistence;

    @CacheEvict(value = "page", key = "contains('#{boardId}')", allEntries = true)
    public Page addPage(Page page, String boardId, String userName) {
        logger.info("Creating new page:{},boardId:{},userName:{}", page, boardId, userName);
        pagesPersistence.addPage(page, boardId, userName);
        Page savedPage = pagesPersistence.findById(page.getId(), boardId);
        logger.info("Saved page:{}", savedPage);
        return savedPage;
    }

    @CacheEvict(value = "page", key = "contains('#{boardId}')", allEntries = true)
    public Page modifyPage(Page page, String pageId, String boardId, String userName) {
        logger.info("Modifying pageId:{},boardId:{},page:{},userName:{}", pageId, boardId, page, userName);
        Page originPage = pagesPersistence.findById(pageId, boardId);
        if (originPage == null) {
            logger.info("No page was found.");
            throw new ResourceNotFoundException(PageCodes.PAGE_IS_NOT_EXISTS);
        }
        pagesPersistence.modify(pageId, boardId, page);
        Page savedPage = pagesPersistence.findById(pageId, boardId);
        logger.info("Modified page:{}", savedPage);
        return savedPage;
    }

    @CacheEvict(value = "page", key = "contains('#{boardId}')", allEntries = true)
    public void removePage(String pageId, String boardId, String userName) {
        logger.info("Removing pageId:{},boardId:{},page:{},userName:{}", pageId, boardId, userName);
        Page originPage = pagesPersistence.findById(pageId, boardId);
        if (originPage == null) {
            logger.info("No page was found.");
            throw new ResourceNotFoundException(PageCodes.PAGE_IS_NOT_EXISTS);
        }
        pagesPersistence.removePage(pageId, boardId);
        logger.info("Page:{} was removed successfully.", pageId);
    }

    public Page loadPage(String pageId, String boardId, String userName) {
        logger.info("Loading pageId:{},boardId:{},userName:{}", pageId, boardId, userName);
        Page page = pagesPersistence.findById(pageId, boardId);
        if (page == null) {
            logger.info("No page was found.");
            throw new ResourceNotFoundException(PageCodes.PAGE_IS_NOT_EXISTS);
        }
        logger.info("Loaded page:{}", page);
        return page;
    }
}
