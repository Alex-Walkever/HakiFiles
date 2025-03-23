package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LeaderCardService {
    Optional<LeaderCard> getLeaderCardByCardId(String cardId);

    LeaderCard saveCard(CardDto info);

    void delete(String cardId);

    List<LeaderCard> getCharactersCardsByFilter(Map<String, String> params);
}
