package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.EventCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventCardService {
    Optional<EventCard> getEventCardByCardId(String cardId);

    EventCard saveCard(CardDto info);

    void delete(String cardId);

    List<EventCard> getEventsCardsByFilter(Map<String, String> params);
}
