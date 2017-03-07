package org.thiki.kanban.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by skytao on 03/06/17.
 */
@RestController
public class VerificationController {
    @Autowired
    private VerificationService verificationService;
    @Resource
    private VerificationsResource verificationsResource;

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}/verifications", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Verification verification, @RequestHeader String userName, @PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @PathVariable String acceptanceCriteriaId) throws Exception {
        List<Verification> verificationList = verificationService.addVerification(verification, acceptanceCriteriaId, boardId, userName);
        return Response.post(verificationsResource.toResource(verificationList, boardId, stageId, cardId, acceptanceCriteriaId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/acceptanceCriterias/{acceptanceCriteriaId}/verifications", method = RequestMethod.GET)
    public HttpEntity loadVerificationsByAcceptanceCriterias(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @PathVariable String acceptanceCriteriaId, @RequestHeader String userName) throws Exception {
        List<Verification> verificationList = verificationService.loadVerificationsByAcceptanceCriteria(acceptanceCriteriaId);
        return Response.build(verificationsResource.toResource(verificationList, boardId, stageId, cardId, acceptanceCriteriaId, userName));
    }
}
