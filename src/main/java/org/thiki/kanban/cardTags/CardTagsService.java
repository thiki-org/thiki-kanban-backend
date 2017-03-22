package org.thiki.kanban.cardTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaCodes;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.tag.Tag;
import org.thiki.kanban.tag.TagsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@Service
public class CardTagsService {
    public static Logger logger = LoggerFactory.getLogger(CardTagsService.class);

    @Resource
    private CardTagPersistence cardTagPersistence;
    @Resource
    private TagsService tagsService;
    @Resource
    private CardsService cardsService;
    @Resource
    private ActivityService activityService;

    @CacheEvict(value = "card-tag", key = "contains('#boardId')", allEntries = true)
    public List<CardTag> stickTags(List<CardTag> cardTags, String cardId, String boardId, String userName) {
        logger.info("Stick tags to card.tags:{},cardId:{},boardId:{}", cardTags, cardId, boardId);
        boolean isCardArchivedOrDone = cardsService.isCardArchivedOrDone(cardId);
        if (isCardArchivedOrDone) {
            throw new BusinessException(AcceptanceCriteriaCodes.CARD_WAS_ALREADY_DONE_OR_ARCHIVED);
        }
        cardTagPersistence.removeTagsByCardId(cardId);
        for (CardTag cardTag : cardTags) {
            cardTagPersistence.stick(cardId, cardTag, userName);
        }
        List<CardTag> tags = loadTags(cardId, boardId);
        logger.info("Stick tags:{}", tags);
        activityService.recordTags(tags, cardId, userName);
        return tags;
    }

    @Cacheable(value = "card-tag", key = "'card-tags'+#cardId+#boardId")
    public List<CardTag> loadTags(String cardId, String boardId) {
        logger.info("Loading card tags.cardId:{},boardId:{}", cardId, boardId);
        List<CardTag> cardTags = cardTagPersistence.findByCardId(cardId);
        for (CardTag cardTag : cardTags) {
            Tag tag = tagsService.findById(cardTag.getTagId());
            cardTag.setColor(tag.getColor());
            cardTag.setName(tag.getName());
        }
        logger.info("Loaded card tags:{}", cardTags);
        return cardTags;
    }
}
