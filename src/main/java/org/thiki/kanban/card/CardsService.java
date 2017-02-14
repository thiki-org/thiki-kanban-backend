package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresPersistence;
import org.thiki.kanban.procedure.ProceduresService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Service
public class CardsService {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaService.class);
    @Resource
    private CardsPersistence cardsPersistence;

    @Resource
    private ProceduresPersistence proceduresPersistence;

    @Resource
    private ActivityService activityService;

    @Resource
    private BoardsService boardsService;
    @Resource
    private DateService dateService;

    @Resource
    private ProceduresService proceduresService;

    @CacheEvict(value = "card", key = "contains('#procedureId')", allEntries = true)
    public Card create(String userName, String boardId, String procedureId, Card card) {
        logger.info("Creating new card:{},procedure:{}", card, procedureId);
        card.setProcedureId(procedureId);
        Procedure procedure = proceduresPersistence.findById(procedureId);
        if (procedure == null) {
            throw new ResourceNotFoundException("procedure[" + procedureId + "] is not found.");
        }
        String code = generateCode(boardId);
        card.setCode(code);
        if (proceduresService.isReachedWipLimit(procedureId)) {
            throw new BusinessException(CardsCodes.PROCEDURE_WIP_REACHED_LIMIT);
        }
        cardsPersistence.create(userName, card);
        Card savedCard = cardsPersistence.findById(card.getId());
        logger.info("Created card:{}", savedCard);
        activityService.recordCardCreation(savedCard, userName);
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

    @CacheEvict(value = "card", key = "contains(#card.procedureId)", allEntries = true)
    public Card modify(String cardId, Card card, String procedureId, String boardId, String userName) {
        logger.info("modify card:{}", card);
        Card originCard = loadAndValidateCard(cardId);
        if (card.isMoveToOtherProcedure(originCard)) {
            if (proceduresService.isReachedWipLimit(card.getProcedureId())) {
                throw new BusinessException(CardsCodes.PROCEDURE_WIP_REACHED_LIMIT);
            }
        }
        card.setCode(originCard.stillNoCode() ? generateCode(boardId) : originCard.getCode());

        cardsPersistence.modify(cardId, card);
        Card savedCard = cardsPersistence.findById(cardId);
        logger.info("Modified card:{}", savedCard);
        activityService.recordCardModification(savedCard, originCard, userName);
        return savedCard;
    }

    @CacheEvict(value = "card", key = "contains('#cardId')", allEntries = true)
    public int deleteById(String cardId) {
        loadAndValidateCard(cardId);
        logger.info("Deleting card.cardId:{}", cardId);
        return cardsPersistence.deleteById(cardId);
    }

    @Cacheable(value = "card", key = "'cards'+#procedureId")
    public List<Card> findByProcedureId(String procedureId) {
        logger.info("Loading cards by procedureId:{}", procedureId);
        Procedure procedure = proceduresPersistence.findById(procedureId);
        if (procedure == null) {
            throw new ResourceNotFoundException(MessageFormat.format("procedure[{0}] is not found.", procedureId));
        }
        List<Card> cards = cardsPersistence.findByProcedureId(procedureId);
        logger.info("The cards belongs from the procedure {} are {}", procedureId, cards);
        return cards;
    }

    public Card findById(String cardId) {
        return loadAndValidateCard(cardId);
    }

    private Card loadAndValidateCard(String cardId) {
        Card foundCard = cardsPersistence.findById(cardId);
        if (foundCard == null) {
            throw new BusinessException(CardsCodes.CARD_IS_NOT_EXISTS);
        }
        return foundCard;
    }

    @CacheEvict(value = "card", key = "contains(#boardId)", allEntries = true)
    public List<Card> resortCards(List<Card> cards, String procedureId, String boardId, String userName) {
        for (Card card : cards) {
            Card foundCard = cardsPersistence.findById(card.getId());
            if (card.isMoveToOtherProcedure(foundCard)) {
                if (proceduresService.isReachedWipLimit(card.getProcedureId())) {
                    throw new BusinessException(CardsCodes.PROCEDURE_WIP_REACHED_LIMIT);
                }
            }
            cardsPersistence.resort(card);
            activityService.recordCardArchive(foundCard, procedureId, userName);
        }
        return findByProcedureId(procedureId);
    }
}
