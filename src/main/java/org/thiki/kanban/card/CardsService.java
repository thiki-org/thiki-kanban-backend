package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.activity.ActivityType;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresPersistence;

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

    @CacheEvict(value = "card", key = "contains('#procedureId')", allEntries = true)
    public Card create(String userName, String procedureId, Card card) {
        logger.info("Creating new card:{},procedure:{}", card, procedureId);
        card.setProcedureId(procedureId);
        Procedure procedure = proceduresPersistence.findById(procedureId);
        if (procedure == null) {
            throw new ResourceNotFoundException("procedure[" + procedureId + "] is not found.");
        }
        Card newCard = procedure.addCard(card);
        cardsPersistence.create(userName, newCard);
        Card savedCard = cardsPersistence.findById(newCard.getId());
        logger.info("Created card:{}", savedCard);
        activityService.record(ActivityType.CARD_CREATION, savedCard, userName);
        return savedCard;
    }

    @CacheEvict(value = "card", key = "contains(#card.procedureId)", allEntries = true)
    public Card modify(String cardId, Card card, String procedureId, String userName) {
        logger.info("modify card:{}", card);
        Card originCard = loadAndValidateCard(cardId);
        if (card.getCode() != null) {
            boolean isCoedAlreadyExist = cardsPersistence.isCodeAlreadyExist(cardId, card.getCode(), card.getProcedureId());
            if (isCoedAlreadyExist) {
                throw new BusinessException(CardsCodes.CODE_IS_ALREADY_EXISTS);
            }
        }
        cardsPersistence.modify(cardId, card);
        Card savedCard = cardsPersistence.findById(cardId);
        logger.info("Modified card:{}", savedCard);
        activityService.record(ActivityType.CARD_MODIFYING, savedCard, originCard, procedureId, userName);
        return savedCard;
    }

    @CacheEvict(value = "card", key = "contains('#cardId')", allEntries = true)
    public int deleteById(String cardId) {
        loadAndValidateCard(cardId);
        return cardsPersistence.deleteById(cardId);
    }

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
    public List<Card> resortCards(List<Card> cards, String procedureId, String boardId) {
        for (Card card : cards) {
            cardsPersistence.resort(card);
        }
        return findByProcedureId(procedureId);
    }
}
