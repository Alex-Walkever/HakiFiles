package org.hakifiles.api.domain.repositories.card.category.leader;

import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderCardRepository extends JpaRepository<LeaderCard, String> {
}
