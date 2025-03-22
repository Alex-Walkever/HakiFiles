package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.card.category.character.CharacterCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CharacterCardServiceImpl implements CharacterCardService {
    @Autowired
    CharacterCardRepository repository;

    @Override
    public Optional<CharacterCard> getCharacterCardByCardId(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public CharacterCard saveCard(CardDto info) {
        CharacterCard characterCard = CardMapper.toCharacterCard(info);
        repository.save(characterCard);
        return characterCard;
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }

    @Override
    public List<CharacterCard> getCharactersCardsByFilter(Map<String, String> params) {
        return repository.customFindMethod(params);
    }
}
