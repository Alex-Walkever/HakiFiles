package org.hakifiles.api.domain.repositories.card.category.stage;

import org.hakifiles.api.domain.entities.card.category.StageCard;

import java.util.List;
import java.util.Map;

public interface CustomStageCardRepository {
    List<StageCard> customFindMethod(Map<String, String> params);
}
