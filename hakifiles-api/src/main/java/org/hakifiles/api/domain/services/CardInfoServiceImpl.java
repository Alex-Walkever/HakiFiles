package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardInfoServiceImpl implements CardInfoService {
    @Autowired
    private CardInfoRepository repository;

    @Override
    public List<CardInfo> getCardsByPage(PaginationDto pagination) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return (List<CardInfo>) repository.findAll(p).stream().toList();
    }

    @Override
    public List<CardInfo> getCardsByCategory(PaginationDto pagination, CardInfo.Category category) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return repository.findByCategory(category, p);
    }

    @Override
    public List<CardInfo> getCardsByColor(PaginationDto pagination, CardInfo.ColorCard color) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByRarity(PaginationDto pagination, CardInfo.Rarity rarity) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByCost(PaginationDto pagination, Integer cost) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsLessOrEqualCost(PaginationDto pagination, Integer cost) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsGreatOrEqualCost(PaginationDto pagination, Integer cost) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByPower(PaginationDto pagination, Integer power) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsLessOrEqualPower(PaginationDto pagination, Integer power) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsGreatOrEqualPower(PaginationDto pagination, Integer power) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByName(PaginationDto pagination, String name) {
        return List.of();
    }

    @Override
    public CardInfo getCardByCardId(String cardId) {
        return null;
    }

    @Override
    public List<CardInfo> getCardsByType(PaginationDto pagination, String type) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByProduct(PaginationDto pagination, String product) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByLife(PaginationDto pagination, Integer life) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByCounterPower(PaginationDto pagination, Integer counterPower) {
        return List.of();
    }

    @Override
    public List<CardInfo> getCardsByAttribute(PaginationDto pagination, String attribute) {
        return List.of();
    }

    @Override
    public CardInfo saveCard(CardDto info) {
        return CardMapper.toCardInfo(info);
    }

    @Override
    public void deleteCard(Long id) {

    }

    @Override
    public CardInfo getCardById(Long id) {
        return null;
    }
}
