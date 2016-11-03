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

    @RequestMapping(value = "/{userName}/worktileTasks", method = RequestMethod.POST)
    public HttpEntity importTasks(@PathVariable("userName") String userName, @RequestParam(value = "worktileTasks", required = false) Object worktileTasks) throws Exception {
        Board savedWorktileCards = worktileService.importWorktileTasks(userName, (MultipartFile) worktileTasks);
        return Response.post(new BoardResource(savedWorktileCards, userName));
    }
}
