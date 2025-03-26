package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.repositories.DeckListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckListServiceImpl implements DeckListService {
    @Autowired
    DeckListRepository repository;

    @Override
    public DeckList saveDeckList(DeckList deckList) {
        return repository.save(deckList);
    }
}
