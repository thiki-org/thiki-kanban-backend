package org.thiki.kanban.cardTags;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@Service
public class CardTagsService {

    @Resource
    private CardTagPersistence cardTagPersistence;

    public List<CardTag> stickTags(List<CardTag> cardTags, String cardId, String userName) {
        cardTagPersistence.removeTagsByCardId(cardId);
        for (CardTag cardTag : cardTags) {
            cardTagPersistence.stick(cardId, cardTag, userName);
        }
        return cardTagPersistence.findByCardId(cardId);
    }

    public List<CardTag> loadTags(String cardId) {
        return cardTagPersistence.findByCardId(cardId);
    }
}
