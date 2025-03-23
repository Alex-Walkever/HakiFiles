package org.hakifiles.api.domain.repositories.card.category.event;

import org.hakifiles.api.domain.entities.card.category.EventCard;

import java.util.List;
import java.util.Map;

public interface CustomEventCardRepository {
    List<EventCard> customFindMethod(Map<String, String> params);
}
