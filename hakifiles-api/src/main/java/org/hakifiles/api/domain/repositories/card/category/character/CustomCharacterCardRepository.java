package org.hakifiles.api.domain.repositories.card.category.character;

import org.hakifiles.api.domain.entities.card.category.CharacterCard;

import java.util.List;
import java.util.Map;

public interface CustomCharacterCardRepository {
    List<CharacterCard> customFindMethod(Map<String, String> params);
}
