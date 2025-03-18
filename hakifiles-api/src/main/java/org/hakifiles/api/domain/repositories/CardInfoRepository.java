package org.hakifiles.api.domain.repositories;

import org.hakifiles.api.domain.entities.CardInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
    //@Query("select u from CardInfo u where u.category = ?1")
    List<CardInfo> findByCategory(CardInfo.Category category, Pageable pageable);

    Optional<CardInfo> findByCardId(String cardId);
}
