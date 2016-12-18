package org.thiki.kanban.tag;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/7/16.
 */
@Service
public class TagResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(Tag tag, String boardId, String userName) throws IOException {
        TagResource tagResource = new TagResource();
        tagResource.domainObject = tag;
        if (tag != null) {
            Link selfLink = linkTo(methodOn(TagsController.class).findById(boardId, tag.getId(), userName)).withSelfRel();
            tagResource.add(tlink.from(selfLink).build(userName));

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId, userName)).withRel("tags");
            tagResource.add(tlink.from(tagsLink).build(userName));
        }
        return tagResource.getResource();
    }
}
