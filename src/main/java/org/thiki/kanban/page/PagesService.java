package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.foundation.exception.BusinessException;

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
        Page savedPage = pagesPersistence.findById(page.getId());
        logger.info("Saved page:{}", savedPage);
        return savedPage;
    }

    @CacheEvict(value = "page", key = "contains('#boardId')", allEntries = true)
    public Page modify(String pageId, Page page, String boardId, String userName) {
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
}
