package org.thiki.kanban.acceptanceCriteria;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/17/16.
 */
public class AcceptanceCriteriaResource extends RestResource {
    public AcceptanceCriteriaResource(AcceptanceCriteria acceptanceCriteria, String boardId, String procedureId, String cardId) throws Exception {
        this.domainObject = acceptanceCriteria;
        if (acceptanceCriteria != null) {
            Link selfLink = linkTo(methodOn(AcceptCriteriaController.class).findById(boardId, procedureId, cardId, acceptanceCriteria.getId())).withSelfRel();
            this.add(selfLink);
            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, cardId)).withRel("acceptanceCriterias");
            this.add(acceptanceCriteriasLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
            this.add(cardLink);
        }
    }

    public AcceptanceCriteriaResource(String boardId, String procedureId, String cardId) throws Exception {
        Link acceptanceCriteriasLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, cardId)).withRel("acceptanceCriterias");
        this.add(acceptanceCriteriasLink);
    }
}
