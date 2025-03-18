package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;

import java.util.Optional;

public interface CharacterCardService {
    Optional<CharacterCard> getCharacterCardByCardId(String cardId);

    CharacterCard saveCard(CardDto info);

    void delete(String cardId);
}
