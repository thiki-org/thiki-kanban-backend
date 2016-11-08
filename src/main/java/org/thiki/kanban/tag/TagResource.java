package org.thiki.kanban.tag;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/7/16.
 */
public class TagResource extends RestResource {
    public TagResource(Tag tag, String userName) throws IOException {
        this.domainObject = tag;
        if (tag != null) {
            Link selfLink = linkTo(methodOn(TagsController.class).findById(userName, tag.getId())).withSelfRel();
            this.add(selfLink);

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByUserName(userName)).withRel("tags");
            this.add(tagsLink);
        }
    }
}
