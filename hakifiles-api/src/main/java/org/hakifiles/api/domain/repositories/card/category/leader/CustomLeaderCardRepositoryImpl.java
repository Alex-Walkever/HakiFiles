package org.hakifiles.api.domain.repositories.card.category.leader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hakifiles.api.infrastructure.tools.CreatePredicate.createIntPredicate;

public class CustomLeaderCardRepositoryImpl implements CustomLeaderCardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LeaderCard> customFindMethod(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LeaderCard> cq = cb.createQuery(LeaderCard.class);
        Root<LeaderCard> leader = cq.from(LeaderCard.class);
        cq.select(leader);

        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("name")) {
            String name = params.get("name");
            if (name != null) {
                predicates.add(cb.like(leader.get("name"), "%" + name + "%"));
            }
        }
        if (params.containsKey("type")) {
            String type = params.get("type");
            if (type != null) {
                List<String> types = new ArrayList<>(Arrays.stream(type.split(",")).toList());

                Join<LeaderCard, String> joinType = leader.join("type");
                predicates.add(cb.and(joinType.in(types)));
            }
        }
        if (params.containsKey("effects")) {
            String effects = params.get("effects");
            if (effects != null) {
                predicates.add(cb.like(leader.get("effects"), "%" + effects + "%"));
            }
        }
        if (params.containsKey("life")) {
            String costString = params.get("life");
            if (costString != null) {
                Predicate predicate = createIntPredicate("life", costString, cb, leader);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        if (params.containsKey("power")) {
            String powerString = params.get("power");
            if (powerString != null) {
                Predicate predicate = createIntPredicate("power", powerString, cb, leader);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        if (params.containsKey("attribute")) {
            String type = params.get("attribute");
            if (type != null) {
                List<String> attributesString = new ArrayList<>(Arrays.stream(type.split(",")).toList());
                List<CardInfo.Attribute> attributes = new ArrayList<>();
                for (String as : attributesString) {
                    attributes.add(CardInfo.Attribute.valueOf(as));
                }
                Join<LeaderCard, CardInfo.Attribute> joinType = leader.join("attribute");
                predicates.add(cb.and(joinType.in(attributes)));
            }
        }

        if (predicates.isEmpty()) {
            return new ArrayList<>();
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).getResultList();
    }
}
