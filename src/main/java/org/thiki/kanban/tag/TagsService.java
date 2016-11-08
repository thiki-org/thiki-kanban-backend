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

    public Tag loadAcceptanceCriteriaById(String acceptanceCriteriaId) {
        return tagPersistence.findById(acceptanceCriteriaId);
    }

    public Tag updateAcceptCriteria(String acceptanceCriteriaId, Tag tag) {
        tagPersistence.updateAcceptCriteria(acceptanceCriteriaId, tag);
        return tagPersistence.findById(acceptanceCriteriaId);
    }

    public Integer removeAcceptanceCriteria(String acceptanceCriteriaId) {
        return tagPersistence.deleteAcceptanceCriteria(acceptanceCriteriaId);
    }

    public List<Tag> resortAcceptCriterias(String cardId, List<Tag> tags) {
        for (Tag tag : tags) {
            tagPersistence.resort(tag);
        }
        return tagPersistence.loadTagsByUserName(cardId);
    }
}
