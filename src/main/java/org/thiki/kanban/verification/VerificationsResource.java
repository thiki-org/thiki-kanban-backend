package org.thiki.kanban.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by skytao on 03/06/17.
 */
@Service
public class VerificationsResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(VerificationsResource.class);

    @Resource
    private TLink tlink;
    @Resource
    private VerificationResource verificationResourceService;

    @Cacheable(value = "acceptanceCriteria", key = "verifications+#boardId+#stageId+#cardId+#userName")
    public Object toResource(List<Verification> verifications, String boardId, String stageId, String cardId, String acceptanceCriteriaId, String userName) throws Exception {
        logger.info("build verifications.verifications:{},stageId:{},cardId:{},userName:{}", verifications, boardId, stageId, cardId, userName);
        VerificationsResource verificationsResource = new VerificationsResource();
        List<Object> verificationResources = new ArrayList<>();
        for (Verification verification : verifications) {
            Object acceptanceCriteriaResource = verificationResourceService.toResource(verification, boardId, stageId, cardId, acceptanceCriteriaId, userName);
            verificationResources.add(acceptanceCriteriaResource);
        }

        verificationsResource.buildDataObject("verifications", verificationResources);

        Link selfLink = linkTo(methodOn(VerificationController.class).loadVerificationsByAcceptanceCriterias(boardId, stageId, cardId, acceptanceCriteriaId, userName)).withSelfRel();
        verificationsResource.add(tlink.from(selfLink).build(userName));

        Link acceptanceCriteriaLink = linkTo(methodOn(AcceptanceCriteriaController.class).findById(boardId, stageId, cardId, acceptanceCriteriaId, userName)).withRel("acceptanceCriteria");
        verificationsResource.add(tlink.from(acceptanceCriteriaLink).build(userName));

        logger.info("verifications build completed.boardId:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return verificationsResource.getResource();
    }
}
