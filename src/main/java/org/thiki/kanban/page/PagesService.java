package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;

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

    @CacheEvict(value = "page", key = "contains('#pageId')", allEntries = true)
    public int deleteById(String pageId) {

        return pagesPersistence.deleteById(pageId);
    }

    @CacheEvict(value = "page", key = "contains('#{boardId}')", allEntries = true)
    public Page modifyPage(Page page, String pageId, String boardId, String userName) {
        logger.info("Modifying pageId:{},boardId:{},page:{},userName:{}", pageId, boardId, page, userName);
        pagesPersistence.modify(pageId, boardId, page);
        Page savedPage = pagesPersistence.findById(pageId, boardId);
        logger.info("Modified page:{}", savedPage);
        return savedPage;
    }
}
