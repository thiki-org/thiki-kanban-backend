package org.thiki.kanban.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 11/7/16.
 */
@Service
public class TagsService {
    private static Logger logger = LoggerFactory.getLogger(TagsService.class);
    @Resource
    private TagPersistence tagPersistence;

    @CacheEvict(value = "tag", key = "contains('#boardId')", allEntries = true)
    public Tag createTag(String boardId, Tag tag) {
        checkUnique(boardId, tag);
        tagPersistence.addTag(boardId, tag);
        return tagPersistence.findById(tag.getId());
    }

    @Cacheable(value = "tag", key = "'service-tags'+#boardId")
    public List<Tag> loadTagsByBoard(String boardId) {
        logger.info("Loading tags of the board [{}]", boardId);
        List<Tag> tags = tagPersistence.loadTagsByBoard(boardId);
        logger.info("The tags of the board are {}", tags);
        return tags;
    }

    @CacheEvict(value = "tag", key = "contains('#tagId')", allEntries = true)
    public Tag updateTag(String boardId, String tagId, Tag tag) {
        checkUnique(boardId, tag);
        tagPersistence.updateTag(tagId, tag);
        return tagPersistence.findById(tagId);
    }

    private void checkUnique(String boardId, Tag tag) {
        boolean isNameDuplicate = tagPersistence.isNameDuplicate(boardId, tag);
        if (isNameDuplicate) {
            throw new BusinessException(TagsCodes.NAME_IS_ALREADY_EXIST);
        }

        boolean isColorDuplicate = tagPersistence.isColorDuplicate(boardId, tag);
        if (isColorDuplicate) {
            throw new BusinessException(TagsCodes.COLOR_IS_ALREADY_EXIST);
        }
    }

    @CacheEvict(value = "tag", key = "contains('#tagId')", allEntries = true)
    public Integer deleteTag(String boardId, String tagId) {
        return tagPersistence.deleteTag(tagId);
    }

    public void cloneTagsFromOtherBoard(String boardId, String sourceBoardId) {
        List<Tag> tags = loadTagsByBoard(sourceBoardId);
        List<Tag> existingTags = loadTagsByBoard(boardId);
        for (Tag tag : tags) {
            if (existingTags.contains(tag)) {
                continue;
            }
            tagPersistence.addTag(boardId, tag);
        }
    }
}
