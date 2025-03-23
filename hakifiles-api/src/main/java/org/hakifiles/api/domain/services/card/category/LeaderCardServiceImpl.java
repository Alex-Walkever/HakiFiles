package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.card.category.leader.LeaderCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaderCardServiceImpl implements LeaderCardService {
    @Autowired
    LeaderCardRepository repository;

    @Override
    public Optional<LeaderCard> getLeaderCardByCardId(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public LeaderCard saveCard(CardDto info) {
        LeaderCard leaderCard = CardMapper.toLeaderCard(info);
        repository.save(leaderCard);
        return leaderCard;
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }

    @Override
    public List<LeaderCard> getCharactersCardsByFilter(Map<String, String> params) {
        return repository.customFindMethod(params);
    }
}
