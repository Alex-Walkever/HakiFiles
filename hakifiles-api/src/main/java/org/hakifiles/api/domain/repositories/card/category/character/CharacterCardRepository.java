package org.hakifiles.api.domain.repositories.card.category.character;

import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CharacterCardRepository extends JpaRepository<CharacterCard, String>, JpaSpecificationExecutor<CharacterCard>, CustomCharacterCardRepository {

}
