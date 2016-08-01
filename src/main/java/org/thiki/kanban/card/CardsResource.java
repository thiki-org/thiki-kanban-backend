package org.thiki.kanban.card;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 *
 */
public class CardsResource extends RestResource {
    public CardsResource(List<Card> cardList, String entryId) {
        this.domainObject = cardList;
        JSONArray cardsJSONArray = new JSONArray();
        for (Card card : cardList) {
            CardResource cardResource = new CardResource(card, entryId);
            JSONObject cardJSON = cardResource.getResource();
            cardsJSONArray.add(cardJSON);
        }
        this.resourcesJSON = cardsJSONArray;
    }
}
