package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.card.category.StageCard;
import org.hakifiles.api.domain.mappers.CardMapper;
import org.hakifiles.api.domain.repositories.card.category.StageCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StageCardServiceImpl implements StageCardService {
    @Autowired
    StageCardRepository repository;

    @Override
    public Optional<StageCard> getStageCardByCardId(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public StageCard saveCard(CardDto info) {
        StageCard stageCard = CardMapper.toStageCard(info);
        repository.save(stageCard);
        return stageCard;
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }
}
