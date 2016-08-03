package org.thiki.kanban.procedure;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class ProceduresService {

    @Resource
    private ProceduresPersistence proceduresPersistence;

    public Procedure create(Integer reporterUserId, final Procedure procedure) {
        procedure.setReporter(reporterUserId);
        proceduresPersistence.create(procedure);
        return proceduresPersistence.findById(procedure.getId());
    }

    public Procedure findById(String id) {
        return proceduresPersistence.findById(id);
    }

    public List<Procedure> loadByBoardId(String boardId) {
        return proceduresPersistence.loadByBoardId(boardId);
    }

    public Procedure update(Procedure procedure) {
        Procedure foundProcedure = checkingWhetherProcedureIsExists(procedure.getId());
        proceduresPersistence.update(procedure);
        if (foundProcedure.getOrderNumber() != procedure.getOrderNumber()) {
            int increment = procedure.getOrderNumber() > foundProcedure.getOrderNumber() ? 1 : 0;
            Map<String, Object> resort = ImmutableMap.<String, Object>builder()
                    .put("boardId", procedure.getBoardId())
                    .put("originOrderNumber", foundProcedure.getOrderNumber())
                    .put("currentOrderNumber", procedure.getOrderNumber())
                    .put("increment", increment)
                    .put("id", procedure.getId())
                    .build();
            proceduresPersistence.resort(resort);
        }
        return proceduresPersistence.findById(procedure.getId());
    }

    private Procedure checkingWhetherProcedureIsExists(String id) {
        Procedure foundProcedure = proceduresPersistence.findById(id);
        if (foundProcedure == null) {
            throw new ResourceNotFoundException("procedure[" + id + "] is not found.");
        }
        return foundProcedure;
    }

    public int deleteById(String id) {
        checkingWhetherProcedureIsExists(id);
        return proceduresPersistence.deleteById(id);
    }
}
