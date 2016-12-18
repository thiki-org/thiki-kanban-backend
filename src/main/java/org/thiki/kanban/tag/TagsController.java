package org.thiki.kanban.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by xubt on 11/7/16.
 */
@RestController
public class TagsController {

    @Autowired
    private TagsService tagsService;
    @Resource
    private TagResource tagResource;
    @Resource
    private TagsResource tagsResource;

    @RequestMapping(value = "/boards/{boardId}/tags", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Tag tag, @PathVariable("boardId") String boardId, @RequestHeader String userName) throws IOException {
        Tag savedTag = tagsService.createTag(boardId, tag);

        return Response.post(tagResource.toResource(savedTag, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/tags/clone", method = RequestMethod.POST)
    public HttpEntity cloneTagsFromOtherBoard(@PathVariable("boardId") String boardId, @RequestParam("sourceBoardId") String sourceBoardId, @RequestHeader String userName) throws IOException {
        tagsService.cloneTagsFromOtherBoard(boardId, sourceBoardId);

        return Response.build(tagsResource.toResource(boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/tags/{tagId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("boardId") String boardId, @PathVariable("tagId") String tagId, @RequestHeader String userName) throws IOException {
        return null;
    }

    @RequestMapping(value = "/boards/{boardId}/tags", method = RequestMethod.GET)
    public HttpEntity loadTagsByBoard(@PathVariable("boardId") String boardId, @RequestHeader String userName) throws IOException {
        List<Tag> tags = tagsService.loadTagsByBoard(boardId);
        return Response.build(tagsResource.toResource(tags, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/tags/{tagId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Tag tag, @PathVariable String boardId, @PathVariable("tagId") String tagId, @RequestHeader String userName) throws Exception {
        Tag updatedTag = tagsService.updateTag(boardId, tagId, tag);
        return Response.build(tagResource.toResource(updatedTag, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/tags/{tagId}", method = RequestMethod.DELETE)
    public HttpEntity deleteTag(@PathVariable("boardId") String boardId, @PathVariable("tagId") String tagId, @RequestHeader String userName) throws IOException {
        tagsService.deleteTag(boardId, tagId);
        return Response.build(tagsResource.toResource(boardId, userName));
    }
}
