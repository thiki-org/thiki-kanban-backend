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
    public HttpEntity create(@RequestBody AcceptanceCriteria acceptanceCriteria, @RequestHeader String userName, @PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.addAcceptCriteria(userName, cardId, acceptanceCriteria);

        return Response.post(new AcceptanceCriteriaResource(savedAcceptanceCriteria, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.PUT)
    public HttpEntity updateAcceptCriteria(@RequestBody AcceptanceCriteria acceptanceCriteria, @PathVariable("cardId") String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId, @PathVariable("procedureId") String procedureId) {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.updateAcceptCriteria(acceptanceCriteriaId, acceptanceCriteria);

        return Response.build(new AcceptanceCriteriaResource(savedAcceptanceCriteria, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("cardId") String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId, @PathVariable("procedureId") String procedureId) {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.loadAcceptanceCriteriaById(acceptanceCriteriaId);

        return Response.build(new AcceptanceCriteriaResource(savedAcceptanceCriteria, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias", method = RequestMethod.GET)
    public HttpEntity loadAcceptanceCriteriasByCardId(@PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        return Response.build(new AcceptCriteriasResource(acceptanceCriteriaList, cardId, procedureId));
    }
}
