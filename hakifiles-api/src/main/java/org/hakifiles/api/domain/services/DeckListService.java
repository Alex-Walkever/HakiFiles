package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.entities.DeckList;

import java.util.Optional;

public interface DeckListService {
    DeckList saveDeckList(DeckList deckList);

    Optional<DeckList> getDeckListById(String id);

    void removeDeckList(String id);
}
