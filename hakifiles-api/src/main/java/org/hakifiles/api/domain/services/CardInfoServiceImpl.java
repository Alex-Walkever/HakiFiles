package org.hakifiles.api.domain.services;

import ch.qos.logback.core.joran.sanity.Pair;
import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.DeckList;
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
    public List<CardInfo> getCardsByProduct(String product) {
        return repository.findByProduct(product);
    }


    @Override
    public CardInfo saveCard(CardDto info) {
        CardInfo cardInfo = CardMapper.toCardInfo(info);
        repository.save(cardInfo);
        return cardInfo;
    }

    @Override
    public CardInfo editCard(CardDto info, CardInfo cardInfo) {
        CardInfo newCardInfo = CardMapper.toCardInfo(info);
        newCardInfo.setId(cardInfo.getId());
        repository.save(newCardInfo);
        return newCardInfo;
    }

    @Override
    public CardInfo editCard(CardInfo cardInfo) {
        return repository.save(cardInfo);
    }

    @Override
    public void removeUsage(DeckList deckList) {
        CardInfo leader = deckList.getLeader();
        leader.removeCardUsage(1L);
        repository.save(leader);
        Map<String, Integer> list = deckList.getList();
        for (Map.Entry<String, Integer> pair : list.entrySet()) {
            Optional<CardInfo> byCardId = repository.findByCardId(pair.getKey());
            if (byCardId.isPresent()) {
                CardInfo cardInfo = byCardId.get();
                cardInfo.removeCardUsage(pair.getValue().longValue());
                repository.save(cardInfo);
            }
        }
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
