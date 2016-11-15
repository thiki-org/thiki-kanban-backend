package org.thiki.kanban.comment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.user.UsersController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
public class CommentResource extends RestResource {
    public static final String URL_TEMPLATE = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/comments/{commentId}";

    public CommentResource(Comment comment, String boardId, String procedureId, String cardId) throws Exception {
        this.domainObject = comment;
        if (comment != null) {
            Link selfLink = linkTo(methodOn(CommentController.class).findById(boardId, procedureId, cardId, comment.getId())).withSelfRel();
            this.add(selfLink);
            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, cardId)).withRel("comments");
            this.add(commentsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
            this.add(cardLink);

            Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(comment.getAuthor())).withRel("avatar");
            this.add(avatarLink);
        }
    }

    public CommentResource(String boardId, String procedureId, String cardId) throws Exception {
        Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, cardId)).withRel("comments");
        this.add(commentsLink);
    }
}
