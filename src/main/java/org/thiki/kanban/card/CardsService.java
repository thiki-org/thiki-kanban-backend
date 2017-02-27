package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.stage.Stage;
import org.thiki.kanban.stage.StagesService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CardsService {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaService.class);
    @Resource
    private CardsPersistence cardsPersistence;
    @Resource
    private ActivityService activityService;
    @Resource
    private BoardsService boardsService;
    @Resource
    private DateService dateService;
    @Resource
    private StagesService stagesService;

    @Resource
    private AcceptanceCriteriaService acceptanceCriteriaService;

    @CacheEvict(value = "card", key = "contains('#stageId')", allEntries = true)
    public Card create(String userName, String boardId, String stageId, Card card) {
        logger.info("Creating new card:{},stage:{}", card, stageId);
        card.setStageId(stageId);
        String code = generateCode(boardId);
        card.setCode(code);
        if (stagesService.isReachedWipLimit(stageId)) {
            throw new BusinessException(CardsCodes.STAGE_WIP_REACHED_LIMIT);
        }
        cardsPersistence.create(userName, card);
        Card savedCard = cardsPersistence.findById(card.getId());
        logger.info("Created card:{}", savedCard);
        Stage stage = stagesService.findById(stageId);
        activityService.recordCardCreation(savedCard, stage, userName);
        return savedCard;
    }

    private String generateCode(String boardId) {
        Board board = boardsService.findById(boardId);
        String currentMonth = dateService.simpleDate();
        int cardsTotal = cardsPersistence.totalCardsIncludingDeleted(boardId, currentMonth);
        int current = cardsTotal + 1;
        if (cardsTotal < 10) {
            return board.getCodePrefix() + currentMonth + "0" + current;
        }
        return board.getCodePrefix() + currentMonth + current;
    }

    @CacheEvict(value = "card", key = "contains(#card.stageId)", allEntries = true)
    public Card modify(String cardId, Card card, String stageId, String boardId, String userName) {
        logger.info("modify card:{}", card);
        Card originCard = loadAndValidateCard(cardId);
        if (card.isMoveToOtherStage(originCard)) {
            if (stagesService.isReachedWipLimit(card.getStageId())) {
                throw new BusinessException(CardsCodes.STAGE_WIP_REACHED_LIMIT);
            }
        }
        if (card.moveToParent(originCard)) {
            Optional<Card> parentCard = Optional.ofNullable(cardsPersistence.findById(card.getParentId()));
            if (!parentCard.isPresent()) {
                throw new BusinessException(CardsCodes.PARENT_CARD_IS_NOT_FOUND);
            }
            boolean isHasChildCard = cardsPersistence.hasChild(cardId);
            if (isHasChildCard) {
                throw new BusinessException(CardsCodes.HAS_CHILD_CARD);
            }
        }
        card.setCode(originCard.stillNoCode() ? generateCode(boardId) : originCard.getCode());

        cardsPersistence.modify(cardId, card);
        Card savedCard = cardsPersistence.findById(cardId);
        logger.info("Modified card:{}", savedCard);
        Stage stage = stagesService.findById(stageId);
        activityService.recordCardModification(savedCard, stage, null, originCard, userName);
        return savedCard;
    }

    @CacheEvict(value = "card", key = "contains('#cardId')", allEntries = true)
    public int deleteById(String cardId) {
        loadAndValidateCard(cardId);
        logger.info("Deleting card.cardId:{}", cardId);
        return cardsPersistence.deleteById(cardId);
    }

    @Cacheable(value = "card", key = "'cards'+#stageId")
    public List<Card> findByStageId(String stageId) {
        logger.info("Loading cards by stageId:{}", stageId);
        List<Card> cards = cardsPersistence.findByStageId(stageId);
        logger.info("The cards belongs from the stage {} are {}", stageId, cards);
        return cards;
    }

    @Cacheable(value = "card", key = "'card'+#cardId")
    public Card findById(String cardId) {
        return loadAndValidateCard(cardId);
    }

    private Card loadAndValidateCard(String cardId) {
        Optional<Card> originCard = Optional.ofNullable(cardsPersistence.findById(cardId));
        if (!originCard.isPresent()) {
            throw new BusinessException(CardsCodes.CARD_IS_NOT_EXISTS);
        }
        return originCard.get();
    }

    @CacheEvict(value = "card", key = "contains(#boardId)", allEntries = true)
    @Caching(evict = {@CacheEvict(value = "card", key = "contains(#boardId)", allEntries = true), @CacheEvict(value = "card", key = "contains(#stageId)", allEntries = true)})
    public List<Card> resortCards(List<Card> cards, String stageId, String boardId, String userName) {
        for (Card card : cards) {
            Card originCard = cardsPersistence.findById(card.getId());
            Stage preStage = null, currentStage = null;
            if (card.isMoveToOtherStage(originCard)) {
                preStage = stagesService.findById(originCard.getStageId());
                currentStage = stagesService.findById(card.getStageId());
                if (stagesService.isReachedWipLimit(currentStage.getId())) {
                    throw new BusinessException(CardsCodes.STAGE_WIP_REACHED_LIMIT);
                }
                if (currentStage.isInDoneStatus()) {
                    boolean isAllAcceptanceCriteriasCompleted = acceptanceCriteriaService.isAllAcceptanceCriteriasCompleted(card.getId());
                    if (!isAllAcceptanceCriteriasCompleted) {
                        throw new BusinessException(CardsCodes.ACCEPTANCE_CRITERIAS_IS_NOT_COMPLETED);
                    }
                }
            }
            cardsPersistence.resort(card);
            activityService.recordCardModification(card, currentStage, preStage, originCard, userName);
        }
        return findByStageId(stageId);
    }

    public List<Card> findByParentId(String cardId) {
        logger.info("Loading child cards.{}", cardId);
        List<Card> childCards = cardsPersistence.loadChildCards(cardId);
        logger.info("Child cards loading completed.cards:{}", childCards);
        return childCards;
    }

    @Cacheable(value = "card", key = "'card-archived'+#cardId")
    public boolean isArchived(String cardId) {
        return cardsPersistence.isArchived(cardId);
    }
}
