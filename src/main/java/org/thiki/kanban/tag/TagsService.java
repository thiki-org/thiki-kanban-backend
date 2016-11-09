package org.thiki.kanban.tag;

import org.springframework.stereotype.Service;

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
        tagPersistence.addTag(boardId, tag);
        return tagPersistence.findById(tag.getId());
    }

    public List<Tag> loadTagsByBoard(String boardId) {
        List<Tag> tags = tagPersistence.loadTagsByBoard(boardId);
        return tags;
    }

    public Tag updateTag(String boardId, String tagId, Tag tag) {
        tagPersistence.updateTag(tagId, tag);
        return tagPersistence.findById(tagId);
    }

    public Integer deleteTag(String boardId, String tagId) {
        return tagPersistence.deleteTag(tagId);
    }
}
