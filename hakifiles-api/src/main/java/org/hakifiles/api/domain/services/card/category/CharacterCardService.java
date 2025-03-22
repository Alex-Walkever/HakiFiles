package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CharacterCardService {
    Optional<CharacterCard> getCharacterCardByCardId(String cardId);

    CharacterCard saveCard(CardDto info);

    void delete(String cardId);

    List<CharacterCard> getCharactersCardsByFilter(Map<String, String> params);
}
