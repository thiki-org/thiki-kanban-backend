package org.thiki.kanban.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by skytao on 03/06/17.
 */
@Service
public class VerificationResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(VerificationResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "verification", key = "'verification'+#verification.id+#boardId+#stageId+#cardId+#acceptanceCriteriaId+#userName")
    public Object toResource(Verification verification, String boardId, String stageId, String cardId, String acceptanceCriteriaId, String userName) throws Exception {
        logger.info("build verification resource.board:{},stageId:{},cardId:{},acceptanceCriteriaId:{},userName:{}", boardId, stageId, cardId, acceptanceCriteriaId, userName);
        VerificationResource verificationResource = new VerificationResource();
        verificationResource.domainObject = verification;
        if (verification != null) {
            Link acceptanceCriteriaLink = linkTo(methodOn(AcceptanceCriteriaController.class).findById(boardId, stageId, cardId, acceptanceCriteriaId, userName)).withRel("acceptanceCriteria");
            verificationResource.add(tlink.from(acceptanceCriteriaLink).build(userName));

            Link authorProfileLink = linkTo(methodOn(UsersController.class).loadProfile(verification.getAuthor())).withRel("authorProfile");
            verificationResource.add(tlink.from(authorProfileLink).build(userName));


            Link verificationsLink = linkTo(methodOn(VerificationController.class).loadVerificationsByAcceptanceCriterias(boardId, stageId, cardId, acceptanceCriteriaId, userName)).withRel("verifications");
            verificationResource.add(tlink.from(verificationsLink).build(userName));
        }
        logger.info("verification resource build completed.board:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return verificationResource.getResource();
    }
}
