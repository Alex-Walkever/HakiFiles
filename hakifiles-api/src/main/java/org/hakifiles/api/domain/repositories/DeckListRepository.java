package org.hakifiles.api.domain.repositories;

import org.hakifiles.api.domain.entities.DeckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckListRepository extends JpaRepository<DeckList, String> {
}
