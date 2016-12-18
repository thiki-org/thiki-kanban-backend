package org.thiki.kanban.comment;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
@Service
public class CommentResource extends RestResource {
    public static final String URL_TEMPLATE = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/comments/{commentId}";

    @Resource
    private TLink tlink;

    public Object toResource(Comment comment, String boardId, String procedureId, String cardId, String userName) throws Exception {
        CommentResource commentResource = new CommentResource();
        commentResource.domainObject = comment;
        if (comment != null) {
            Link selfLink = linkTo(methodOn(CommentController.class).findById(boardId, procedureId, cardId, comment.getId(), userName)).withSelfRel();
            commentResource.add(tlink.from(selfLink).build(userName));

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, cardId, userName)).withRel("comments");
            commentResource.add(tlink.from(commentsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
            commentResource.add(tlink.from(cardLink).build(userName));

            Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(comment.getAuthor())).withRel("avatar");
            commentResource.add(tlink.from(avatarLink).build(userName));
        }
        return commentResource.getResource();
    }

    public Object toResource(String boardId, String procedureId, String cardId, String userName) throws Exception {
        CommentResource commentResource = new CommentResource();

        Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, cardId, userName)).withRel("comments");
        commentResource.add(tlink.from(commentsLink).build(userName));
        return commentResource.getResource();
    }
}
