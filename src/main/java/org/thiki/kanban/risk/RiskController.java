package org.thiki.kanban.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by cain on 2017/2/28.
 */
@RestController
public class RiskController {
    @Autowired
    private RiskResource riskResource;

    @Autowired
    private RiskResources riskResources;

    @Autowired
    private RiskService riskService;

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/risk", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Risk risk, @RequestHeader String userName, @PathVariable String boardId,
                             @PathVariable String stageId, @PathVariable String cardId) throws Exception {
        Risk savedRisk = riskService.addRisk(userName, cardId, risk);
        return Response.post(riskResource.toResource(savedRisk, boardId, stageId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/risks/{riskId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId,
                               @PathVariable String riskId, String userName) throws Exception {
        Risk savedRisk = riskService.loadRiskById(riskId);
        return Response.build(riskResource.toResource(savedRisk, boardId, stageId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/risks", method = RequestMethod.GET)
    public HttpEntity loadCardRisks(@RequestHeader String userName, @PathVariable String boardId,
                                    @PathVariable String stageId, @PathVariable String cardId) throws Exception {

        List<Risk> risk = riskService.loadCardRisks(cardId);

        return Response.build(riskResources.toResource(risk, boardId, stageId, cardId, userName));


    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/risks/{riskId}", method = RequestMethod.DELETE)
    public HttpEntity removeRisk(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId,
                                 @PathVariable String riskId, String userName) throws Exception {

        riskService.removeRisk(riskId);
        return Response.build(riskResource.toResource(boardId, stageId, cardId, userName));


    }
}
