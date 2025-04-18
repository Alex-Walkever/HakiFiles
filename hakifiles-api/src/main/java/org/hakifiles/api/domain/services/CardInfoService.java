package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.DeckList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CardInfoService {

    List<CardInfo> getCardsByCategory(PaginationDto pagination, CardInfo.Category category);

    List<CardInfo> getCardsByBlock(PaginationDto pagination, Integer block);

    Optional<CardInfo> getCardByCardId(String cardId);

    List<CardInfo> getCardsByProduct(String product);

    CardInfo saveCard(CardDto info);

    CardInfo editCard(CardDto info, CardInfo cardInfo);

    CardInfo editCard(CardInfo cardInfo);

    void removeUsage(DeckList deckList);

    void deleteCard(Long id);

    List<CardInfo> getCardsByListCardId(List<String> cardsId);

    List<CardInfo> getCardsByFilterAndCardsId(Map<String, String> params, List<String> ids);
}
