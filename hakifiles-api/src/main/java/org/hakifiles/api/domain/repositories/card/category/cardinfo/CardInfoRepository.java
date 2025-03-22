package org.hakifiles.api.domain.repositories.card.category.cardinfo;

import org.hakifiles.api.domain.entities.CardInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
    @Transactional(readOnly = true)
    List<CardInfo> findByCategory(CardInfo.Category category, Pageable pageable);

    @Transactional(readOnly = true)
    List<CardInfo> findByBlock(Integer block, Pageable pageable);

    @Transactional(readOnly = true)
    List<CardInfo> findByProduct(String product, Pageable pageable);

    @Transactional(readOnly = true)
    Optional<CardInfo> findByCardId(String cardId);

    @Transactional(readOnly = true)
    List<CardInfo> findByCardIdIn(List<String> cardsId);
}
