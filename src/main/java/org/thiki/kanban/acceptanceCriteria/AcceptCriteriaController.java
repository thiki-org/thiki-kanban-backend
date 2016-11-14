package org.thiki.kanban.acceptanceCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by xubt on 10/17/16.
 */
@RestController
public class AcceptCriteriaController {

    @Autowired
    private AcceptCriteriaService acceptCriteriaService;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody AcceptanceCriteria acceptanceCriteria, @RequestHeader String userName, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws IOException {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.addAcceptCriteria(userName, cardId, acceptanceCriteria);

        return Response.post(new AcceptanceCriteriaResource(savedAcceptanceCriteria, boardId, procedureId, cardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.PUT)
    public HttpEntity updateAcceptCriteria(@RequestBody AcceptanceCriteria acceptanceCriteria, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId) throws IOException {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.updateAcceptCriteria(acceptanceCriteriaId, acceptanceCriteria);

        return Response.build(new AcceptanceCriteriaResource(savedAcceptanceCriteria, boardId, procedureId, cardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId) throws IOException {
        AcceptanceCriteria savedAcceptanceCriteria = acceptCriteriaService.loadAcceptanceCriteriaById(acceptanceCriteriaId);

        return Response.build(new AcceptanceCriteriaResource(savedAcceptanceCriteria, boardId, procedureId, cardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}", method = RequestMethod.DELETE)
    public HttpEntity removeAcceptanceCriteria(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable("acceptanceCriteriaId") String acceptanceCriteriaId) throws IOException {
        acceptCriteriaService.removeAcceptanceCriteria(acceptanceCriteriaId);

        return Response.build(new AcceptanceCriteriaResource(boardId, procedureId, cardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias", method = RequestMethod.GET)
    public HttpEntity loadAcceptanceCriteriasByCardId(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws IOException {
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        return Response.build(new AcceptCriteriasResource(acceptanceCriteriaList, boardId, procedureId, cardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/acceptanceCriterias/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resortAcceptCriterias(@RequestBody List<AcceptanceCriteria> acceptanceCriterias, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws IOException {
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptCriteriaService.resortAcceptCriterias(cardId, acceptanceCriterias);
        return Response.build(new AcceptCriteriasResource(acceptanceCriteriaList, boardId, procedureId, cardId));
    }
}
