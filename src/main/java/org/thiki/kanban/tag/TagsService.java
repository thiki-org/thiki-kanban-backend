package org.thiki.kanban.tag;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 11/7/16.
 */
@Service
public class TagsService {

    @Resource
    private TagPersistence tagPersistence;

    public Tag createTag(String boardId, Tag tag) {
        checkUnique(boardId, tag);

        tagPersistence.addTag(boardId, tag);
        return tagPersistence.findById(tag.getId());
    }

    public List<Tag> loadTagsByBoard(String boardId) {
        return tagPersistence.loadTagsByBoard(boardId);
    }

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

    public Integer deleteTag(String boardId, String tagId) {
        return tagPersistence.deleteTag(tagId);
    }

    public void cloneTagsFromOtherBoard(String boardId, String fromBoardId) {
        List<Tag> tags = loadTagsByBoard(fromBoardId);
        for (Tag tag : tags) {
            tagPersistence.addTag(boardId, tag);
        }
    }
}
