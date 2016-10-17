package org.thiki.kanban.acceptanceCriteria;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/17/16.
 */
public class AcceptCriteriaResource extends RestResource {
    public AcceptCriteriaResource(AcceptCriteria acceptanceCriteria, String cardId) {
        this.domainObject = acceptanceCriteria;
        if (acceptanceCriteria != null) {
            Link selfLink = linkTo(methodOn(AcceptCriteriaController.class).findById(cardId, acceptanceCriteria.getId())).withSelfRel();
            this.add(selfLink);
            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(cardId)).withRel("acceptanceCriterias");
            this.add(acceptanceCriteriasLink);
        }
    }
}
