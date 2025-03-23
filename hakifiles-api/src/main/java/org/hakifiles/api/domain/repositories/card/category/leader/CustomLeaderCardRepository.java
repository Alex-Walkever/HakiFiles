package org.hakifiles.api.domain.repositories.card.category.leader;

import org.hakifiles.api.domain.entities.card.category.LeaderCard;

import java.util.List;
import java.util.Map;

public interface CustomLeaderCardRepository {
    List<LeaderCard> customFindMethod(Map<String, String> params);
}
