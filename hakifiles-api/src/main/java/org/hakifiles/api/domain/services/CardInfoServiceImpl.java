package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.card.category.cardinfo.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardInfoServiceImpl implements CardInfoService {
    @Autowired
    private CardInfoRepository repository;

    @Override
    public List<CardInfo> getCardsByCategory(PaginationDto pagination, CardInfo.Category category) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return repository.findByCategory(category, p);
    }

    @Override
    public List<CardInfo> getCardsByBlock(PaginationDto pagination, Integer block) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return repository.findByBlock(block, p);
    }

    @Override
    public Optional<CardInfo> getCardByCardId(String cardId) {
        return repository.findByCardId(cardId);
    }

    @Override
    public List<CardInfo> getCardsByProduct(PaginationDto pagination, String product) {
        PageRequest p = PageRequest.of(pagination.getOffSet(), pagination.getLimit());
        return repository.findByProduct(product, p);
    }


    @Override
    public CardInfo saveCard(CardDto info) {
        CardInfo cardInfo = CardMapper.toCardInfo(info);
        repository.save(cardInfo);
        return cardInfo;
    }

    @Override
    public void deleteCard(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CardInfo> getCardsByListCardId(List<String> cardsId) {
        return repository.findByCardIdIn(cardsId);
    }

    @Override
    public List<CardInfo> getCardsByFilterAndCardsId(Map<String, String> params, List<String> ids) {
        List<CardInfo> cardInfos = repository.customFindMethod(params, ids);
        if (cardInfos == null) {
            cardInfos = repository.findByCardIdIn(ids);
        }
        return cardInfos;
    }

}
