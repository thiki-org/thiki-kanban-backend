package org.thiki.kanban.tag;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/7/16.
 */
public class TagsResource extends RestResource {

    public TagsResource(List<Tag> tags, String userName) throws IOException {
        List<TagResource> tagResources = new ArrayList<>();
        for (Tag tag : tags) {
            TagResource tagResource = new TagResource(tag, userName);
            tagResources.add(tagResource);
        }

        this.buildDataObject("tags", tagResources);
        Link selfLink = linkTo(methodOn(TagsController.class).loadTagsByUserName(userName)).withSelfRel();
        this.add(selfLink);
    }

    public TagsResource(String userName) throws IOException {
        Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByUserName(userName)).withRel("tags");
        this.add(tagsLink);
    }
}
