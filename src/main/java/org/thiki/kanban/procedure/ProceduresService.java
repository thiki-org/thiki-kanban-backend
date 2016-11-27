package org.thiki.kanban.procedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class ProceduresService {
    public static Logger logger = LoggerFactory.getLogger(ProceduresService.class);

    @Resource
    private ProceduresPersistence proceduresPersistence;

    public Procedure create(String userName, String boardId, final Procedure procedure) {
        boolean isExists = proceduresPersistence.uniqueTitle(boardId, procedure.getTitle());
        if (isExists) {
            throw new BusinessException(ProcedureCodes.TITLE_IS_ALREADY_EXISTS);
        }
        proceduresPersistence.create(procedure, userName, boardId);
        return proceduresPersistence.findById(procedure.getId());
    }

    public Procedure findById(String id) {
        return proceduresPersistence.findById(id);
    }

    public List<Procedure> loadByBoardId(String boardId) {
        logger.info("Loading the procedures of the board [{}]", boardId);
        List<Procedure> procedures = proceduresPersistence.loadByBoardId(boardId);
        logger.info("The procedures of the board are {}", procedures);
        return procedures;
    }

    public Procedure update(Procedure procedure) {
        checkingWhetherProcedureIsExists(procedure.getId());
        proceduresPersistence.update(procedure);
        return proceduresPersistence.findById(procedure.getId());
    }

    private Procedure checkingWhetherProcedureIsExists(String id) {
        Procedure foundProcedure = proceduresPersistence.findById(id);
        if (foundProcedure == null) {
            throw new BusinessException(ProcedureCodes.PROCEDURE_IS_NOT_EXIST);
        }
        return foundProcedure;
    }

    public int deleteById(String id) {
        checkingWhetherProcedureIsExists(id);
        return proceduresPersistence.deleteById(id);
    }

    public List<Procedure> resortProcedures(List<Procedure> procedures, String boardId) {
        for (Procedure procedure : procedures) {
            proceduresPersistence.resort(procedure);
        }
        return loadByBoardId(boardId);
    }
}
