package org.thiki.kanban.procedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
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

    @CacheEvict(value = "procedure", key = "contains('#boardId')", allEntries = true)
    public Procedure create(String userName, String boardId, final Procedure procedure) {
        logger.info("Checking whether title is already exist before creating new procedure.procedure:{}", procedure);
        boolean isExists = proceduresPersistence.uniqueTitle(boardId, procedure.getTitle());
        if (isExists) {
            logger.info("The title is already exist.");
            throw new BusinessException(ProcedureCodes.TITLE_IS_ALREADY_EXISTS);
        }
        proceduresPersistence.create(procedure, userName, boardId);
        Procedure createdProcedure = proceduresPersistence.findById(procedure.getId());
        logger.info("Created procedure:{}", createdProcedure);
        return createdProcedure;
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

    @CacheEvict(value = "procedure", key = "contains('#procedure.id')", allEntries = true)
    public Procedure modifyProcedure(String procedureId, Procedure newProcedure, String boardId) {
        logger.info("Updating procedure{},procedureId{},boardId{}", newProcedure, procedureId, boardId);
        Procedure originProcedure = checkingWhetherProcedureIsExists(procedureId);
        if (isModifiedToDoneProcedure(newProcedure, originProcedure)) {
            logger.info("Checking whether the procedure  status is in sprint");
            if (isNotSprintProcedure(newProcedure, originProcedure)) {
                throw new BusinessException(ProcedureCodes.PROCEDURE_TYPE_IS_NOT_IN_SPRINT);
            }
            boolean isDoneProcedureAlreadyExist = proceduresPersistence.isDoneProcedureAlreadyExist(procedureId, boardId);
            logger.info("Checking whether the done procedure is already exist:{}", isDoneProcedureAlreadyExist);
            if (isDoneProcedureAlreadyExist) {
                throw new BusinessException(ProcedureCodes.DONE_PROCEDURE_IS_ALREADY_EXIST);
            }
        }
        proceduresPersistence.update(procedureId, newProcedure);
        Procedure updatedProcedure = proceduresPersistence.findById(procedureId);
        logger.info("Updated procedure is {}", updatedProcedure);
        return updatedProcedure;
    }

    private boolean isNotSprintProcedure(Procedure newProcedure, Procedure originProcedure) {
        return !originProcedure.isInSprint() && !newProcedure.isInSprint();
    }

    private boolean isModifiedToDoneProcedure(Procedure procedure, Procedure foundProcedure) {
        return procedure.isDone() && !foundProcedure.isDone();
    }

    private Procedure checkingWhetherProcedureIsExists(String procedureId) {
        logger.info("Checking whether target procedure is exist.procedureId:{}", procedureId);
        Procedure foundProcedure = proceduresPersistence.findById(procedureId);
        if (foundProcedure == null) {
            logger.info("No procedure was found.");
            throw new BusinessException(ProcedureCodes.PROCEDURE_IS_NOT_EXIST);
        }
        logger.info("The found procedure is {}", foundProcedure);
        return foundProcedure;
    }

    @CacheEvict(value = "procedure", key = "contains('#procedureId')", allEntries = true)
    public int deleteById(String procedureId) {
        checkingWhetherProcedureIsExists(procedureId);
        return proceduresPersistence.deleteById(procedureId);
    }

    @CacheEvict(value = "procedure", key = "contains('#boardId')", allEntries = true)
    public List<Procedure> resortProcedures(List<Procedure> procedures, String boardId) {
        for (Procedure procedure : procedures) {
            proceduresPersistence.resort(procedure);
        }
        return loadByBoardId(boardId);
    }
}
