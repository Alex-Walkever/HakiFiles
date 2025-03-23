package org.hakifiles.api.domain.repositories.card.category.cardinfo;

import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;
import java.util.Map;

public interface CustomCardInfoRepository {
    List<CardInfo> customFindMethod(Map<String, String> params, List<String> ids);
}
