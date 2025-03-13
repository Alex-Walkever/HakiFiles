package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.repositories.card.category.CharacterCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterCardServiceImpl implements CharacterCardService {
    @Autowired
    CharacterCardRepository repository;
}
