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

    @Resource
    private ProcedureResource procedureResource;
    @Resource
    private ProceduresResource proceduresResource;
    @Resource
    private ResortProceduresResource resortProceduresResource;

    @RequestMapping(value = "/boards/{boardId}/procedures", method = RequestMethod.GET)
    public HttpEntity loadAll(@PathVariable String boardId, @RequestHeader String userName) throws Exception {
        List<Procedure> procedureList = proceduresService.loadByBoardId(boardId);
        return Response.build(proceduresResource.toResource(procedureList, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        Procedure procedure = proceduresService.findById(id);
        return Response.build(procedureResource.toResource(procedure, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.PUT)
    public HttpEntity<ProcedureResource> update(@RequestBody Procedure procedure, @PathVariable String id, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        procedure.setId(id);
        Procedure updatedProcedure = proceduresService.update(procedure);

        return Response.build(procedureResource.toResource(updatedProcedure, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        proceduresService.deleteById(id);
        return Response.build(procedureResource.toResource(boardId, userName));

    }

    @RequestMapping(value = "/boards/{boardId}/procedures", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Procedure procedure, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        Procedure savedProcedure = proceduresService.create(userName, boardId, procedure);

        return Response.post(procedureResource.toResource(savedProcedure, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resort(@RequestBody List<Procedure> procedures, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        List<Procedure> procedureList = proceduresService.resortProcedures(procedures, boardId);
        return Response.build(resortProceduresResource.toResource(procedureList, boardId, userName));
    }
}
