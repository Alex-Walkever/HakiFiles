package org.hakifiles.api.domain.repositories.card.category.stage;

import org.hakifiles.api.domain.entities.card.category.StageCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageCardRepository extends JpaRepository<StageCard, String>, CustomStageCardRepository {
}
