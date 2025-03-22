package org.hakifiles.api.domain.repositories.card.category.event;

import org.hakifiles.api.domain.entities.card.category.EventCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCardRepository extends JpaRepository<EventCard, String> {
}
