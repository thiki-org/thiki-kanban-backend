package org.thiki.kanban.stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardCodes;
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
public class StagesService {
    public static final String ARCHIVE_SUFFIX = "归档";
    public static Logger logger = LoggerFactory.getLogger(StagesService.class);
    @Resource
    private StagesPersistence stagesPersistence;
    @Resource
    private CardsService cardsService;

    @CacheEvict(value = "stage", key = "contains('#boardId')", allEntries = true)
    public Stage create(String userName, String boardId, final Stage stage) {
        logger.info("Checking whether title is already exist before creating new stage.stage:{}", stage);
        boolean isExists = stagesPersistence.uniqueTitle(boardId, stage.getTitle());
        if (isExists) {
            logger.info("The title is already exist.");
            throw new BusinessException(StageCodes.TITLE_IS_ALREADY_EXISTS);
        }
        stagesPersistence.create(stage, userName, boardId);
        Stage createdStage = stagesPersistence.findById(stage.getId());
        logger.info("Created stage:{}", createdStage);
        return createdStage;
    }

    @Cacheable(value = "stage", key = "'stage'+#stageId")
    public Stage findById(String stageId) {
        return stagesPersistence.findById(stageId);
    }

    @Cacheable(value = "stage", key = "'stages'+#boardId+#viewType")
    public List<Stage> loadByBoardId(String boardId, String viewType) {
        logger.info("Loading the stages of the board [{}]", boardId);

        List<Stage> stages = stagesPersistence.loadByBoardIdAndType(boardId, viewType);
        logger.info("The stages of the board are {}", stages);
        return stages;
    }

    @CacheEvict(value = "stage", key = "contains('#stageId')", allEntries = true)
    public Stage modifyStage(String stageId, Stage newStage, String boardId) {
        logger.info("Updating stage{},stageId{},boardId{}", newStage, stageId, boardId);
        Stage originStage = checkingWhetherStageIsExists(stageId);
        if (isModifyToArchive(newStage, originStage)) {
            throw new BusinessException(StageCodes.NOT_ALLOW_SET_STAGE_TO_ARCHIVE_DIRECTLY);
        }
        if (isModifiedToDoneStage(newStage, originStage)) {
            logger.info("Checking whether the stage  status is in sprint");
            if (isNotSprintStage(newStage, originStage)) {
                throw new BusinessException(StageCodes.STAGE_TYPE_IS_NOT_IN_SPRINT);
            }
            boolean isDoneStageAlreadyExist = stagesPersistence.isDoneStageAlreadyExist(stageId, boardId);
            logger.info("Checking whether the done stage is already exist:{}", isDoneStageAlreadyExist);
            if (isDoneStageAlreadyExist) {
                throw new BusinessException(StageCodes.DONE_STAGE_IS_ALREADY_EXIST);
            }
        }
        stagesPersistence.update(stageId, newStage);
        Stage updatedStage = stagesPersistence.findById(stageId);
        logger.info("Updated stage is {}", updatedStage);
        return updatedStage;
    }

    private boolean isModifyToArchive(Stage newStage, Stage originStage) {
        return newStage.isArchived() && !originStage.isArchived();
    }

    private boolean isNotSprintStage(Stage newStage, Stage originStage) {
        return !originStage.isInSprint() && !newStage.isInSprint();
    }

    private boolean isModifiedToDoneStage(Stage stage, Stage foundStage) {
        return stage.isInDoneStatus() && !foundStage.isInDoneStatus();
    }

    private Stage checkingWhetherStageIsExists(String stageId) {
        logger.info("Checking whether target stage is exist.stageId:{}", stageId);
        Stage foundStage = stagesPersistence.findById(stageId);
        if (foundStage == null) {
            logger.info("No stage was found.");
            throw new BusinessException(StageCodes.STAGE_IS_NOT_EXIST);
        }
        logger.info("The found stage is {}", foundStage);
        return foundStage;
    }

    @CacheEvict(value = "stage", key = "contains('#stageId')", allEntries = true)
    public int deleteById(String stageId) {
        checkingWhetherStageIsExists(stageId);
        return stagesPersistence.deleteById(stageId);
    }

    @CacheEvict(value = "stage", key = "contains('#boardId')", allEntries = true)
    public List<Stage> resortStages(List<Stage> stages, String boardId) {
        logger.info("Resort stages:{}", stages);
        for (Stage stage : stages) {
            stagesPersistence.resort(stage);
        }
        List<Stage> resortedStages = loadByBoardId(boardId, BoardCodes.VIEW_TYPE_SPRINT);
        logger.info("Resorted stages:{}", resortedStages);
        return resortedStages;
    }

    @CacheEvict(value = "stage", key = "contains('#boardId')", allEntries = true)
    public Stage archive(Sprint sprint, String boardId, String userName) {
        logger.info("Archiving stage.boardId:{}", boardId);
        Stage doneStage = findStageByStatus(boardId, StageCodes.STAGE_STATUS_DONE);
        if (doneStage == null) {
            throw new BusinessException(StageCodes.DONE_STAGE_IS_NOT_EXIST);
        }
        Stage archiveStage = new Stage();
        archiveStage.setBoardId(boardId);
        archiveStage.setTitle(sprint.getSprintName() + ARCHIVE_SUFFIX);
        archiveStage.setType(StageCodes.STAGE_TYPE_ARCHIVE);
        archiveStage.setStatus(StageCodes.STAGE_STATUS_DONE);
        archiveStage.setAuthor(userName);
        Stage archivedStage = create(userName, boardId, archiveStage);
        logger.info("Transfer the cards of the origin stage to archived stage.");
        List<Card> cards = cardsService.findByStageId(doneStage.getId());
        for (Card card : cards) {
            card.setStageId(archivedStage.getId());
            cardsService.modify(card.getId(), card, doneStage.getId(), boardId, userName);
        }
        logger.info("Archived stage.stage:{}", archiveStage);
        return archivedStage;
    }

    public Stage findStageByStatus(String boardId, Integer status) {
        return stagesPersistence.findStageByStatus(boardId, status);
    }

    public boolean isReachedWipLimit(String stageId) {
        Stage stage = findById(stageId);
        if (stage == null) {
            throw new BusinessException(StageCodes.STAGE_IS_NOT_EXIST);
        }
        Integer currentCardsNumbers = stagesPersistence.countCardsNumber(stageId);
        return stage.isReachedWipLimit(currentCardsNumbers);
    }
}
