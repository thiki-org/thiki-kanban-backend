package org.thiki.kanban.worktile;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardResource;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 11/1/16.
 */
@RestController
public class WorktileController {
    @Resource
    private WorktileService worktileService;

    @Resource
    private BoardResource boardResource;

    @RequestMapping(value = "/{userName}/projects/{projectId}/worktileTasks", method = RequestMethod.POST)
    public HttpEntity importTasks(@PathVariable("userName") String userName, @PathVariable String projectId, @RequestParam(value = "worktileTasks", required = false) Object worktileTasks) throws Exception {
        Board savedWorktileCards = worktileService.importWorktileTasks(projectId, userName, (MultipartFile) worktileTasks);
        return Response.post(boardResource.toResource(savedWorktileCards, projectId, userName));
    }
}
