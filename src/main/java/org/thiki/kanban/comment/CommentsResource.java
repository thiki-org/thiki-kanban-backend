package org.thiki.kanban.comment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
public class CommentsResource extends RestResource {
    public CommentsResource(List<Comment> comments, String cardId, String procedureId) {
        List<CommentResource> commentResources = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResource commentResource = new CommentResource(comment, cardId, procedureId);
            commentResources.add(commentResource);
        }

        this.buildDataObject("comments", commentResources);
        Link selfLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(cardId, procedureId)).withSelfRel();
        this.add(selfLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
