package org.thiki.kanban.comment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
public class CommentResource extends RestResource {
    public CommentResource(Comment comment, String cardId, String procedureId) {
        this.domainObject = comment;
        if (comment != null) {
            Link selfLink = linkTo(methodOn(CommentController.class).findById(cardId, comment.getId(), procedureId)).withSelfRel();
            this.add(selfLink);
            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(cardId, procedureId)).withRel("comments");
            this.add(commentsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(procedureId, cardId)).withRel("card");
            this.add(cardLink);
        }
    }

    public CommentResource(String cardId, String procedureId) {
        Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(cardId, procedureId)).withRel("comments");
        this.add(commentsLink);
    }
}
