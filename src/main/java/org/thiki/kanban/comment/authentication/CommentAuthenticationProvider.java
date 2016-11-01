package org.thiki.kanban.comment.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.comment.Comment;
import org.thiki.kanban.comment.CommentCodes;
import org.thiki.kanban.comment.CommentResource;
import org.thiki.kanban.comment.CommentService;
import org.thiki.kanban.foundation.exception.AuthenticationException;
import org.thiki.kanban.foundation.security.authentication.AuthenticationProvider;
import org.thiki.kanban.foundation.security.authentication.MethodType;

import javax.annotation.Resource;

/**
 * Created by xubt on 11/1/2016.
 */
@Service("commentAuthenticationProvider")
public class CommentAuthenticationProvider extends AuthenticationProvider {
    @Resource
    private CommentService commentService;

    public String getPathTemplate() {
        return CommentResource.URL_TEMPLATE;
    }

    @Override
    public boolean authenticate(String url, String method, String userName) {
        config(url, userName);
        if (MethodType.DELETE.equal(method)) {
            boolean isAllowedDelete = authDelete();
            if (!isAllowedDelete) {
                throw new AuthenticationException(CommentCodes.AUTH_THE_COMMENT_YOU_WANT_TO_DELETE_IS_NOT_YOURS);
            }
        }
        return true;
    }

    @Override
    public boolean authPost() {
        return false;
    }

    @Override
    public boolean authDelete() {
        Comment comment = commentService.loadCommentById(pathParams.get("commentId"));
        return comment.getAuthor().equals(userName);
    }
}
