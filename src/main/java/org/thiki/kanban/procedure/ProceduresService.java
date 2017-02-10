package org.thiki.kanban.procedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.sprint.Sprint;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class ProceduresService {
    public static final String ARCHIVE_SUFFIX = "归档";
    public static Logger logger = LoggerFactory.getLogger(ProceduresService.class);

    @Resource
    private ProceduresPersistence proceduresPersistence;

    @Resource
    private CardsService cardsService;

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

    public List<Procedure> loadByBoardId(String boardId, String viewType) {
        logger.info("Loading the procedures of the board [{}]", boardId);

        List<Procedure> procedures = proceduresPersistence.loadByBoardIdAndType(boardId, ViewType.value(viewType).value());
        logger.info("The procedures of the board are {}", procedures);
        return procedures;
    }

    @CacheEvict(value = "procedure", key = "contains('#procedureId')", allEntries = true)
    public Procedure modifyProcedure(String procedureId, Procedure newProcedure, String boardId) {
        logger.info("Updating procedure{},procedureId{},boardId{}", newProcedure, procedureId, boardId);
        Procedure originProcedure = checkingWhetherProcedureIsExists(procedureId);
        if (isModifyToArchive(newProcedure, originProcedure)) {
            throw new BusinessException(ProcedureCodes.NOT_ALLOW_SET_PROCEDURE_TO_ARCHIVE_DIRECTLY);
        }
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

    private boolean isModifyToArchive(Procedure newProcedure, Procedure originProcedure) {
        return newProcedure.isArchived() && !originProcedure.isArchived();
    }

    private boolean isNotSprintProcedure(Procedure newProcedure, Procedure originProcedure) {
        return !originProcedure.isInSprint() && !newProcedure.isInSprint();
    }

    private boolean isModifiedToDoneProcedure(Procedure procedure, Procedure foundProcedure) {
        return procedure.isInDoneStatus() && !foundProcedure.isInDoneStatus();
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
        logger.info("Resort procedures:{}", procedures);
        for (Procedure procedure : procedures) {
            proceduresPersistence.resort(procedure);
        }
        List<Procedure> resortedProcedures = loadByBoardId(boardId, ViewType.VIEW_TYPE_SPRINT.type());
        logger.info("Resorted procedures:{}", resortedProcedures);
        return resortedProcedures;
    }

    @CacheEvict(value = "procedure", key = "contains('#boardId')", allEntries = true)
    public Procedure archive(Sprint sprint, String boardId, String userName) {
        logger.info("Archiving procedure.boardId:{}", boardId);
        Procedure doneProcedure = findProcedureByStatus(boardId, ProcedureCodes.PROCEDURE_STATUS_DONE);
        if (doneProcedure == null) {
            throw new BusinessException(ProcedureCodes.DONE_PROCEDURE_IS_NOT_EXIST);
        }
        Procedure archiveProcedure = new Procedure();
        archiveProcedure.setBoardId(boardId);
        archiveProcedure.setTitle(sprint.getSprintName() + ARCHIVE_SUFFIX);
        archiveProcedure.setType(ProcedureCodes.PROCEDURE_TYPE_ARCHIVE);
        archiveProcedure.setStatus(ProcedureCodes.PROCEDURE_STATUS_DONE);
        archiveProcedure.setAuthor(userName);
        Procedure archivedProcedure = create(userName, boardId, archiveProcedure);
        logger.info("Transfer the cards of the origin procedure to archived procedure.");
        List<Card> cards = cardsService.findByProcedureId(doneProcedure.getId());
        for (Card card : cards) {
            card.setProcedureId(archivedProcedure.getId());
            cardsService.modify(card.getId(), card, doneProcedure.getId(), boardId, userName);
        }
        logger.info("Archived procedure.procedure:{}", archiveProcedure);
        return archivedProcedure;
    }

    @CacheEvict(value = "procedure", key = "contains('#archivedProcedureId')", allEntries = true)
    public void undoArchive(String archivedProcedureId, String boardId, String userName) {
        logger.info("Undo archiving.archivedProcedureId:{}", archivedProcedureId);
        Procedure archivedProcedure = proceduresPersistence.findById(archivedProcedureId);
        if (archivedProcedure == null) {
            logger.info("No specified archived procedure was found in board:{},archivedProcedureId:{}", boardId, archivedProcedureId);
            throw new BusinessException(ProcedureCodes.NO_ARCHIVED_PROCEDURE_WAS_FOUND);
        }

        boolean isNewArchivedProcedureExist = proceduresPersistence.hasNewArchivedProcedureExist(archivedProcedure);
        if (isNewArchivedProcedureExist) {
            logger.info("New archived procedure was found in board:{}", boardId);
            throw new BusinessException(ProcedureCodes.NEW_ARCHIVED_PROCEDURE_WAS_FOUND);
        }

        Procedure doneProcedure = proceduresPersistence.findDoneProcedure(boardId);
        if (doneProcedure == null) {
            logger.info("No done procedure was found in board:{}", boardId);
            throw new BusinessException(ProcedureCodes.NO_DONE_PROCEDURE_WAS_FOUND);
        }


        logger.info("Transfer the cards of the archived procedure to done procedure.");
        List<Card> cards = cardsService.findByProcedureId(archivedProcedureId);
        for (Card card : cards) {
            card.setProcedureId(doneProcedure.getId());
            cardsService.modify(card.getId(), card, archivedProcedureId, boardId, userName);
        }
        logger.info("Deleting the archived procedure.");
        proceduresPersistence.deleteById(archivedProcedureId);
    }

    public Procedure findProcedureByStatus(String boardId, Integer status) {
        return proceduresPersistence.findProcedureByStatus(boardId, status);
    }
}
