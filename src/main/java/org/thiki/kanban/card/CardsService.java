package org.thiki.kanban.card;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresPersistence;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Service
public class CardsService {

    @Resource
    private CardsPersistence cardsPersistence;

    @Resource
    private ProceduresPersistence proceduresPersistence;

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

    public Card update(String cardId, Card currentCard) {
        Card originCard = findById(cardId);

        cardsPersistence.update(cardId, currentCard);

        if (isCardMovedAcrossProcedure(currentCard, originCard)) {
            cardsPersistence.resortTargetProcedure(originCard.getId(), currentCard.getProcedureId(), currentCard.getOrderNumber());
            cardsPersistence.resortOriginProcedure(originCard.getId(), originCard.getProcedureId(), originCard.getOrderNumber());
        }
        if (isCardMovedWithinOriginProcedure(currentCard, originCard)) {
            int increment = currentCard.getOrderNumber() > originCard.getOrderNumber() ? 1 : 0;
            cardsPersistence.resortOrder(originCard.getId(), originCard.getProcedureId(), originCard.getOrderNumber(), currentCard.getOrderNumber(), increment);
        }
        return cardsPersistence.findById(cardId);
    }

    private boolean isCardMovedWithinOriginProcedure(Card currentCard, Card originCard) {
        return (currentCard.getProcedureId().equals(originCard.getProcedureId())) && (!currentCard.getOrderNumber().equals(originCard.getOrderNumber()));
    }

    private boolean isCardMovedAcrossProcedure(Card currentCard, Card originCard) {
        return !currentCard.getProcedureId().equals(originCard.getProcedureId());
    }

    public int deleteById(String cardId) {
        loadAndValidateCard(cardId);
        return cardsPersistence.deleteById(cardId);
    }

    public List<Card> findByProcedureId(String procedureId) {
        Procedure procedure = proceduresPersistence.findById(procedureId);
        if (procedure == null) {
            throw new ResourceNotFoundException(MessageFormat.format("procedure[{0}] is not found.", procedureId));
        }
        return cardsPersistence.findByProcedureId(procedureId);
    }

    public Card findById(String cardId) {
        return loadAndValidateCard(cardId);
    }

    private Card loadAndValidateCard(String cardId) {
        Card foundCard = cardsPersistence.findById(cardId);
        if (foundCard == null) {
            throw new ResourceNotFoundException(MessageFormat.format("card[{0}] is not found.", cardId));
        }
        return foundCard;
    }
}
