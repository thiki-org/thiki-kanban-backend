package org.thiki.kanban.comment;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
@Service
public class CommentResource extends RestResource {
    public static final String URL_TEMPLATE = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/comments/{commentId}";

    @Resource
    private TLink tlink;

    @Cacheable(value = "comment", key = "'comment'+#comment.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(Comment comment, String boardId, String stageId, String cardId, String userName) throws Exception {
        CommentResource commentResource = new CommentResource();
        commentResource.domainObject = comment;
        if (comment != null) {
            Link selfLink = linkTo(methodOn(CommentController.class).findById(boardId, stageId, cardId, comment.getId(), userName)).withSelfRel();
            commentResource.add(tlink.from(selfLink).build(userName));

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, stageId, cardId, userName)).withRel("comments");
            commentResource.add(tlink.from(commentsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
            commentResource.add(tlink.from(cardLink).build(userName));

            Link avatarLink = linkTo(UsersController.class, UsersController.class.getMethod("loadAvatar", String.class, HttpServletResponse.class), userName).withRel("avatar");
            commentResource.add(tlink.from(avatarLink).build(userName));
        }
        return commentResource.getResource();
    }

    @Cacheable(value = "comment", key = "'comment'+#comment.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(String boardId, String stageId, String cardId, String userName) throws Exception {
        CommentResource commentResource = new CommentResource();

        Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, stageId, cardId, userName)).withRel("comments");
        commentResource.add(tlink.from(commentsLink).build(userName));
        return commentResource.getResource();
    }
}
