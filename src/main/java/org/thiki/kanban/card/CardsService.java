package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
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

    @CacheEvict(value = "card", key = "contains('#procedureId')", allEntries = true)
    public Card create(String userName, String procedureId, Card card) {
        card.setProcedureId(procedureId);
        Procedure procedure = proceduresPersistence.findById(procedureId);
        if (procedure == null) {
            throw new ResourceNotFoundException("procedure[" + procedureId + "] is not found.");
        }
        Card newCard = procedure.addCard(card);
        cardsPersistence.create(userName, newCard);
        return cardsPersistence.findById(newCard.getId());
    }

    @CacheEvict(value = "card", key = "contains(#card.procedureId)", allEntries = true)
    public Card update(String cardId, Card card) {
        logger.info("update card:{}", card);
        loadAndValidateCard(cardId);
        if (card.getCode() != null) {
            boolean isCoedAlreadyExist = cardsPersistence.isCodeAlreadyExist(cardId, card.getCode(), card.getProcedureId());
            if (isCoedAlreadyExist) {
                throw new BusinessException(CardsCodes.CODE_IS_ALREADY_EXISTS);
            }
        }
        cardsPersistence.update(cardId, card);
        return cardsPersistence.findById(cardId);
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
    @CacheEvict(value = "card", key = "contains(#procedureId)", allEntries = true)
    public List<Card> resortCards(List<Card> cards, String procedureId) {
        for (Card card : cards) {
            cardsPersistence.resort(card);
        }
        return findByProcedureId(procedureId);
    }
}
