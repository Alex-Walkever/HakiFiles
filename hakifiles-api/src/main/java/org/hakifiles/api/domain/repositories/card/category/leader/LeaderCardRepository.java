package org.hakifiles.api.domain.repositories.card.category.leader;

import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LeaderCardRepository extends JpaRepository<LeaderCard, String>, JpaSpecificationExecutor<LeaderCard>, CustomLeaderCardRepository {
    List<LeaderCard> findByNameContaining(String name);
}
