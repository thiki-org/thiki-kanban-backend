package org.thiki.kanban.acceptanceCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by xubt on 10/17/16.
 */
@RestController
public class AcceptCriteriaController {

    @Autowired
    private AcceptCriteriaService acceptCriteriaService;

    @RequestMapping(value = "/cards/{cardId}/acceptanceCriterias", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody AcceptCriteria acceptCriteria, @RequestHeader String userName, @PathVariable("cardId") String cardId) {
        AcceptCriteria savedAcceptCriteria = acceptCriteriaService.addAcceptCriteria(userName, cardId, acceptCriteria);

        return Response.post(new AcceptCriteriaResource(savedAcceptCriteria, cardId));
    }

    @RequestMapping(value = "/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("cardId") String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId) {
        return null;
    }

    @RequestMapping(value = "/cards/{cardId}/acceptanceCriterias", method = RequestMethod.GET)
    public HttpEntity loadAcceptanceCriteriasByCardId(@PathVariable("cardId") String cardId) {
        return null;
    }
}
