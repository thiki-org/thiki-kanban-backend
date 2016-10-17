package org.thiki.kanban.acceptanceCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by xubt on 10/17/16.
 */
@RestController
public class AcceptCriteriaController {

    @Autowired
    private AcceptCriteriaService acceptCriteriaService;

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody AcceptCriteria acceptCriteria, @RequestHeader String userName, @PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        AcceptCriteria savedAcceptCriteria = acceptCriteriaService.addAcceptCriteria(userName, cardId, acceptCriteria);

        return Response.post(new AcceptanceCriteriaResource(savedAcceptCriteria, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("cardId") String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId, @PathVariable("procedureId") String procedureId) {
        return null;
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias", method = RequestMethod.GET)
    public HttpEntity loadAcceptanceCriteriasByCardId(@PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        List<AcceptCriteria> acceptCriteriaList = acceptCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        return Response.build(new AcceptCriteriasResource(acceptCriteriaList, cardId, procedureId));
    }
}
