package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;
import java.util.Optional;

public interface CardInfoService {

    List<CardInfo> getCardsByCategory(PaginationDto pagination, CardInfo.Category category);

    List<CardInfo> getCardsByBlock(PaginationDto pagination, Integer block);

    Optional<CardInfo> getCardByCardId(String cardId);

    List<CardInfo> getCardsByProduct(PaginationDto pagination, String product);

    CardInfo saveCard(CardDto info);

    void deleteCard(Long id);

    List<CardInfo> getCardsByListCardId(List<String> cardsId);
}
