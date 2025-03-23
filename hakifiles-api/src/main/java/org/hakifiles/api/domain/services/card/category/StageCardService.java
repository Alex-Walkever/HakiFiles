package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.StageCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StageCardService {
    Optional<StageCard> getStageCardByCardId(String cardId);

    StageCard saveCard(CardDto info);

    void delete(String cardId);

    List<StageCard> getStagesCardsByFilter(Map<String, String> params);
}
