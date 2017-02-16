package org.thiki.kanban.comment;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 10/31/16.
 */
@Service
public class CommentsResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private CommentResource commentResourceService;

    @Cacheable(value = "comment", key = "'comments'+#boardId+#stageId+#cardId+#userName")
    public Object toResource(List<Comment> comments, String boardId, String stageId, String cardId, String userName) throws Exception {
        CommentsResource commentsResource = new CommentsResource();
        List<Object> commentResources = new ArrayList<>();
        for (Comment comment : comments) {
            Object commentResource = commentResourceService.toResource(comment, boardId, stageId, cardId, userName);
            commentResources.add(commentResource);
        }

        commentsResource.buildDataObject("comments", commentResources);
        Link selfLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, stageId, cardId, userName)).withSelfRel();
        commentsResource.add(tlink.from(selfLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
        commentsResource.add(tlink.from(cardLink).build(userName));
        return commentsResource.getResource();
    }
}
