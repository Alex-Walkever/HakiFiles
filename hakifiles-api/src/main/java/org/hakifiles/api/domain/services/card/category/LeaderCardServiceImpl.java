package org.hakifiles.api.domain.services.card.category;

import org.hakifiles.api.domain.repositories.card.category.LeaderCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderCardServiceImpl implements LeaderCardService {
    @Autowired
    LeaderCardRepository repository;
}
