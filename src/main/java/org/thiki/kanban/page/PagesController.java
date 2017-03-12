package org.thiki.kanban.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by winie on 2017/2/6.
 */
@RestController
@RequestMapping(value = "")
public class PagesController {
    @Autowired
    private PagesService pagesService;
    @Resource
    private PageResource pageResource;

    @Resource
    private PagesResource pagesResource;

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/pages", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Page page, @PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Page savedPage = pagesService.addPage(page, boardId, userName);
        return Response.post(pageResource.toResource(savedPage, boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/pages/{pageId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String pageId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Page page = pagesService.loadPage(pageId, boardId, userName);
        return Response.build(pageResource.toResource(page, boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/pages/{pageId}", method = RequestMethod.DELETE)
    public HttpEntity removePage(@PathVariable String boardId, @PathVariable String pageId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        pagesService.removePage(pageId, boardId, userName);
        return Response.build(pageResource.toResource(boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/pages", method = RequestMethod.GET)
    public HttpEntity findByBoard(@PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        List<Page> pages = pagesService.loadPages(boardId, userName);
        return Response.build(pagesResource.toResource(pages, boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/pages/{pageId}", method = RequestMethod.PUT)
    public HttpEntity modify(@RequestBody Page page, @PathVariable String pageId, @PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Page savedPage = pagesService.modifyPage(page, pageId, boardId, userName);
        return Response.build(pageResource.toResource(savedPage, boardId, projectId, userName));
    }
}
