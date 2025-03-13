package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;

public interface CardInfoService {
    List<CardInfo> getCardsByPage(PaginationDto pagination);

    List<CardInfo> getCardsByCategory(PaginationDto pagination, CardInfo.Category category);

    List<CardInfo> getCardsByColor(PaginationDto pagination, CardInfo.ColorCard color);

    List<CardInfo> getCardsByRarity(PaginationDto pagination, CardInfo.Rarity rarity);

    List<CardInfo> getCardsByCost(PaginationDto pagination, Integer cost);

    List<CardInfo> getCardsLessOrEqualCost(PaginationDto pagination, Integer cost);

    List<CardInfo> getCardsGreatOrEqualCost(PaginationDto pagination, Integer cost);

    List<CardInfo> getCardsByPower(PaginationDto pagination, Integer power);

    List<CardInfo> getCardsLessOrEqualPower(PaginationDto pagination, Integer power);

    List<CardInfo> getCardsGreatOrEqualPower(PaginationDto pagination, Integer power);

    List<CardInfo> getCardsByName(PaginationDto pagination, String name);

    CardInfo getCardByCardId(String cardId);

    List<CardInfo> getCardsByType(PaginationDto pagination, String type);

    List<CardInfo> getCardsByProduct(PaginationDto pagination, String product);

    List<CardInfo> getCardsByLife(PaginationDto pagination, Integer life);

    List<CardInfo> getCardsByCounterPower(PaginationDto pagination, Integer counterPower);

    List<CardInfo> getCardsByAttribute(PaginationDto pagination, String attribute);

    CardInfo saveCard(CardInfo info);

    void deleteCard(Long id);

    CardInfo getCardById(Long id);
}
