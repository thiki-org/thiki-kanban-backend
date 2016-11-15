package org.thiki.kanban.acceptanceCriteria;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/17/16.
 */
public class AcceptCriteriasResource extends RestResource {
    public AcceptCriteriasResource(List<AcceptanceCriteria> acceptanceCriterias, String boardId, String procedureId, String cardId) throws Exception {
        List<AcceptanceCriteriaResource> acceptanceCriteriaResources = new ArrayList<>();
        for (AcceptanceCriteria acceptanceCriteria : acceptanceCriterias) {
            AcceptanceCriteriaResource acceptanceCriteriaResource = new AcceptanceCriteriaResource(acceptanceCriteria, boardId,procedureId,cardId);
            acceptanceCriteriaResources.add(acceptanceCriteriaResource);
        }

        this.buildDataObject("acceptanceCriterias", acceptanceCriteriaResources);
        Link selfLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, cardId)).withSelfRel();
        this.add(selfLink);

        Link sortNumbersLink = linkTo(methodOn(AcceptCriteriaController.class).resortAcceptCriterias(null, boardId, procedureId, cardId)).withRel("sortNumbers");
        this.add(sortNumbersLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId,procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
