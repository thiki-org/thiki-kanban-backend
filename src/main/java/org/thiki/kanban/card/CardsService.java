package org.thiki.kanban.card;

import org.springframework.stereotype.Service;
import org.thiki.kanban.entry.EntriesPersistence;
import org.thiki.kanban.entry.Entry;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Service
public class CardsService {

    @Resource
    private CardsPersistence cardsPersistence;

    @Resource
    private EntriesPersistence entriesPersistence;

    public Card create(Integer reporterUserId, String entryId, Card card) {
        card.setReporter(reporterUserId);
        card.setEntryId(entryId);
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException("entry[" + entryId + "] is not found.");
        }
        Card newCard = entry.addCard(card);
        cardsPersistence.create(newCard);
        return cardsPersistence.findById(newCard.getId());
    }

    public Card update(String cardId, Card currentCard) {
        Card originCard = findById(cardId);

        cardsPersistence.update(cardId, currentCard);

        if (isCardMovedAcrossEntry(currentCard, originCard)) {
            cardsPersistence.resortTargetEntry(originCard.getId(), currentCard.getEntryId(), currentCard.getOrderNumber());
            cardsPersistence.resortOriginEntry(originCard.getId(), originCard.getEntryId(), originCard.getOrderNumber());
        }
        if (isCardMovedWithinOriginEntry(currentCard, originCard)) {
            int increment = currentCard.getOrderNumber() > originCard.getOrderNumber() ? 1 : 0;
            cardsPersistence.resortOrder(originCard.getId(), originCard.getEntryId(), originCard.getOrderNumber(), currentCard.getOrderNumber(), increment);
        }
        return cardsPersistence.findById(cardId);
    }

    private boolean isCardMovedWithinOriginEntry(Card currentCard, Card originCard) {
        return (currentCard.getEntryId().equals(originCard.getEntryId())) && (!currentCard.getOrderNumber().equals(originCard.getOrderNumber()));
    }

    private boolean isCardMovedAcrossEntry(Card currentCard, Card originCard) {
        return !currentCard.getEntryId().equals(originCard.getEntryId());
    }

    public int deleteById(String cardId) {
        loadAndValidateCard(cardId);
        return cardsPersistence.deleteById(cardId);
    }

    public List<Card> findByEntryId(String entryId) {
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException(MessageFormat.format("entry[{0}] is not found.", entryId));
        }
        return cardsPersistence.findByEntryId(entryId);
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
