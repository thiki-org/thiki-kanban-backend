package org.thiki.kanban.acceptanceCriteria;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubt on 10/17/16.
 */
public class AcceptCriteriasResource extends RestResource {
    public AcceptCriteriasResource(List<AcceptCriteria> cardList, String procedureId) {
        this.domainObject = cardList;
        JSONArray cardsJSONArray = new JSONArray();
        for (AcceptCriteria card : cardList) {
            AcceptCriteriaResource acceptCriteriaResource = new AcceptCriteriaResource(card, procedureId);
            JSONObject cardJSON = acceptCriteriaResource.getResource();
            cardsJSONArray.add(cardJSON);
        }
        this.resourcesJSON = cardsJSONArray;
    }
}
