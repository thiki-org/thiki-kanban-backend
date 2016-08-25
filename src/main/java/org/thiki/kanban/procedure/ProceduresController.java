package org.thiki.kanban.procedure;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
public class ProceduresController {
    @Resource
    private ProceduresService proceduresService;

    @RequestMapping(value = "/boards/{boardId}/procedures", method = RequestMethod.GET)
    public HttpEntity loadAll(@PathVariable String boardId) {
        List<Procedure> procedureList = proceduresService.loadByBoardId(boardId);
        return Response.build(new ProceduresResource(procedureList, boardId));

    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String boardId) {
        Procedure procedure = proceduresService.findById(id);
        return Response.build(new ProcedureResource(procedure, boardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.PUT)
    public HttpEntity<ProcedureResource> update(@RequestBody Procedure procedure, @PathVariable String id, @PathVariable String boardId) {
        procedure.setId(id);
        Procedure updatedProcedure = proceduresService.update(procedure);

        return Response.build(new ProcedureResource(updatedProcedure, boardId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @PathVariable String boardId) {
        proceduresService.deleteById(id);
        return Response.build(new ProcedureResource(boardId));

    }

    @RequestMapping(value = "/boards/{boardId}/procedures", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Procedure procedure, @RequestHeader String userName, @PathVariable String boardId) {
        Procedure savedProcedure = proceduresService.create(userName, procedure);

        return Response.post(new ProcedureResource(savedProcedure, boardId));
    }
}
