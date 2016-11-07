package org.thiki.kanban.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.io.IOException;

/**
 * Created by xubt on 11/7/16.
 */
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/{userName}/tags", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Tag tag, @PathVariable("userName") String userName) throws IOException {
        Tag savedTag = tagService.createTag(userName, tag);

        return Response.post(new TagResource(savedTag, userName));
    }

    @RequestMapping(value = "/{userName}/tags/{tagId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("userName") String userName, @PathVariable("tagId") String tagId) throws IOException {
        return null;
    }

    @RequestMapping(value = "/{userName}/tags", method = RequestMethod.GET)
    public HttpEntity loadTagsByUserName(@PathVariable("userName") String userName) throws IOException {
        return null;
    }
}
