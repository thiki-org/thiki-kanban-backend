package org.thiki.kanban.acceptanceCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.verification.VerificationController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/17/16.
 */
@Service
public class AcceptanceCriteriaResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriteria'+#acceptanceCriteria.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(AcceptanceCriteria acceptanceCriteria, String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build acceptanceCriterias resource.board:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        AcceptanceCriteriaResource acceptanceCriteriaResource = new AcceptanceCriteriaResource();
        acceptanceCriteriaResource.domainObject = acceptanceCriteria;
        if (acceptanceCriteria != null) {
            Link selfLink = linkTo(methodOn(AcceptanceCriteriaController.class).findById(boardId, stageId, cardId, acceptanceCriteria.getId(), userName)).withSelfRel();
            acceptanceCriteriaResource.add(tlink.from(selfLink).build(userName));
            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, stageId, cardId, userName)).withRel("acceptanceCriterias");
            acceptanceCriteriaResource.add(tlink.from(acceptanceCriteriasLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
            acceptanceCriteriaResource.add(tlink.from(cardLink).build(userName));

            Link verificationsLink = linkTo(methodOn(VerificationController.class).loadVerificationsByAcceptanceCriterias(boardId, stageId, cardId, acceptanceCriteria.getId(), userName)).withRel("verifications");
            acceptanceCriteriaResource.add(tlink.from(verificationsLink).build(userName));
        }
        logger.info("acceptanceCriterias resource build completed.board:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return acceptanceCriteriaResource.getResource();
    }

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriteria'+#boardId+#stageId+#cardId+#userName")
    public Object toResource(String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build acceptanceCriterias resource.board:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        AcceptanceCriteriaResource acceptanceCriteriaResource = new AcceptanceCriteriaResource();
        Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, stageId, cardId, userName)).withRel("acceptanceCriterias");
        acceptanceCriteriaResource.add(tlink.from(acceptanceCriteriasLink).build(userName));
        logger.info("acceptanceCriterias resource build completed.board:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return acceptanceCriteriaResource.getResource();
    }
}
