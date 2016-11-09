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

    public Tag createTag(String userName, Tag tag) {
        tagPersistence.addTag(userName, tag);
        return tagPersistence.findById(tag.getId());
    }

    public List<Tag> loadTagsByUserName(String userName) {
        List<Tag> tags = tagPersistence.loadTagsByUserName(userName);
        return tags;
    }

    public Tag updatePersonalTag(String userName, String tagId, Tag tag) {
        tagPersistence.updateTag(tagId, tag);
        return tagPersistence.findById(tagId);
    }

    public Integer deletePersonalTag(String userName, String tagId) {
        return tagPersistence.deleteTag(tagId);
    }
}
