package org.thiki.kanban.acceptanceCriteria;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/17/16.
 */
@Service
public class AcceptanceCriteriaResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(AcceptanceCriteria acceptanceCriteria, String boardId, String procedureId, String cardId, String userName) throws Exception {
        AcceptanceCriteriaResource acceptanceCriteriaResource = new AcceptanceCriteriaResource();
        acceptanceCriteriaResource.domainObject = acceptanceCriteria;
        if (acceptanceCriteria != null) {
            Link selfLink = linkTo(methodOn(AcceptanceCriteriaController.class).findById(boardId, procedureId, cardId, acceptanceCriteria.getId(), userName)).withSelfRel();
            acceptanceCriteriaResource.add(tlink.from(selfLink).build(userName));
            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, cardId, userName)).withRel("acceptanceCriterias");
            acceptanceCriteriaResource.add(tlink.from(acceptanceCriteriasLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
            acceptanceCriteriaResource.add(tlink.from(cardLink).build(userName));
        }
        return acceptanceCriteriaResource.getResource();
    }

    public Object toResource(String boardId, String procedureId, String cardId, String userName) throws Exception {
        AcceptanceCriteriaResource acceptanceCriteriaResource = new AcceptanceCriteriaResource();
        Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, cardId, userName)).withRel("acceptanceCriterias");
        acceptanceCriteriaResource.add(tlink.from(acceptanceCriteriasLink).build(userName));
        return acceptanceCriteriaResource.getResource();
    }
}
