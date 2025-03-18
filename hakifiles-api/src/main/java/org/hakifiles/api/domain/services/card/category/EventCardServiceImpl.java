package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.EventCard;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.card.category.EventCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventCardServiceImpl implements EventCardService {
    @Autowired
    EventCardRepository repository;

    @Override
    public Optional<EventCard> getEventCardByCardId(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public EventCard saveCard(CardDto info) {
        EventCard eventCard = CardMapper.toEventCard(info);
        repository.save(eventCard);
        return eventCard;
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }
}
