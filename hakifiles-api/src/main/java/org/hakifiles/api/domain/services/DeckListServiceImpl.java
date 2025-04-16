package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.repositories.DeckListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckListServiceImpl implements DeckListService {
    @Autowired
    DeckListRepository repository;

    @Override
    public DeckList saveDeckList(DeckList deckList) {
        return repository.save(deckList);
    }

    @Override
    public Optional<DeckList> getDeckListById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<DeckList> getAllDecksWithId(List<String> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void removeDeckList(String id) {
        repository.deleteById(id);
    }
}
