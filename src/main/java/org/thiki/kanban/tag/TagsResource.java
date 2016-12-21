package org.thiki.kanban.tag;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/7/16.
 */
@Service
public class TagsResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private TagResource tagResourceService;

    @Cacheable(value = "tag", key = "#userName+#boardId+'-tags'")
    public Object toResource(List<Tag> tags, String boardId, String userName) throws IOException {
        TagsResource tagsResource = new TagsResource();
        List<Object> tagResources = new ArrayList<>();
        for (Tag tag : tags) {
            Object tagResource = tagResourceService.toResource(tag, boardId, userName);
            tagResources.add(tagResource);
        }

        tagsResource.buildDataObject("tags", tagResources);
        Link selfLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId, userName)).withSelfRel();
        tagsResource.add(tlink.from(selfLink).build(userName));

        Link cloneLink = linkTo(methodOn(TagsController.class).cloneTagsFromOtherBoard(boardId, "", userName)).withRel("clone");
        tagsResource.add(tlink.from(cloneLink).build(userName));
        return tagsResource.getResource();
    }

    @Cacheable(value = "tag", key = "#userName+#boardId")
    public Object toResource(String boardId, String userName) throws IOException {
        TagsResource tagsResource = new TagsResource();
        Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId, userName)).withRel("tags");
        tagsResource.add(tlink.from(tagsLink).build(userName));
        return tagsResource.getResource();
    }
}
