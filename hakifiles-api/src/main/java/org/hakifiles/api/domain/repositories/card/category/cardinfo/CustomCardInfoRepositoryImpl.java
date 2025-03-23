package org.hakifiles.api.domain.repositories.card.category.cardinfo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.infrastructure.tools.CreatePredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomCardInfoRepositoryImpl implements CustomCardInfoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CardInfo> customFindMethod(Map<String, String> params, List<String> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CardInfo> cq = cb.createQuery(CardInfo.class);
        Root<CardInfo> card = cq.from(CardInfo.class);
        cq.select(card);

        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("category")) {
            String categories = params.get("category");
            if (categories != null) {
                List<String> categoryStringList = new ArrayList<>(Arrays.stream(categories.split(",")).toList());
                List<CardInfo.Category> categoryList = new ArrayList<>();
                for (String s : categoryStringList) {
                    categoryList.add(CardInfo.Category.valueOf(s));
                }
                predicates.add(cb.and(card.get("category").in(categoryList)));
            }
        }

        if (params.containsKey("product")) {
            String products = params.get("product");
            if (products != null) {
                List<String> productList = new ArrayList<>(Arrays.stream(products.split(",")).toList());
                predicates.add(cb.and(card.get("product").in(productList)));
            }
        }
        if (params.containsKey("set")) {
            String series = params.get("set");
            if (series != null) {
                List<String> setList = new ArrayList<>(Arrays.stream(series.split(",")).toList());
                predicates.add(cb.and(card.get("productCode").in(setList)));
            }
        }
        if (params.containsKey("series")) {
            String series = params.get("series");
            if (series != null) {
                List<String> seriesStringList = new ArrayList<>(Arrays.stream(series.split(",")).toList());
                List<CardInfo.Series> seriesList = new ArrayList<>();
                for (String s : seriesStringList) {
                    seriesList.add(CardInfo.Series.valueOf(s));
                }
                predicates.add(cb.and(card.get("series").in(seriesList)));
            }
        }
        if (params.containsKey("rarity")) {
            String rarities = params.get("rarity");
            if (rarities != null) {
                List<String> rarityStringList = new ArrayList<>(Arrays.stream(rarities.split(",")).toList());
                List<CardInfo.Rarity> rarityList = new ArrayList<>();
                for (String r : rarityStringList) {
                    rarityList.add(CardInfo.Rarity.valueOf(r));
                }
                predicates.add(cb.and(card.get("rarity").in(rarityList)));
            }
        }

        if (params.containsKey("color")) {
            String colors = params.get("color");
            if (colors != null) {
                List<String> colorStringList = new ArrayList<>(Arrays.stream(colors.split(",")).toList());
                List<CardInfo.ColorCard> colorCardList = new ArrayList<>();
                for (String s : colorStringList) {
                    colorCardList.add(CardInfo.ColorCard.valueOf(s));
                }
                Join<CardInfo, CardInfo.ColorCard> joinColor = card.join("colorCards");
                predicates.add(cb.and(joinColor.in(colorCardList)));
            }
        }

        if (params.containsKey("block")) {
            String block = params.get("block");
            if (block != null) {
                Predicate predicate = CreatePredicate.createIntPredicate("block", block, cb, card);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }

        if (params.containsKey("status")) {
            String status = params.get("status");
            if (status != null) {
                predicates.add(cb.equal(card.get("tournamentStatus"), CardInfo.TournamentStatus.valueOf(status)));
            }
        }

        if (predicates.isEmpty()) {
            return null;
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<CardInfo> resultList = entityManager.createQuery(cq).getResultList();

        if (ids.isEmpty()) {
            return resultList;
        }

        return resultList.stream().filter(e -> ids.contains(e.getCardId())).collect(Collectors.toList());
    }
}
