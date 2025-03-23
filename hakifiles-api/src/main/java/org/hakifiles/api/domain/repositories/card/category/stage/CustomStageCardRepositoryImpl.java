package org.hakifiles.api.domain.repositories.card.category.stage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hakifiles.api.domain.entities.card.category.StageCard;
import org.hakifiles.api.infrastructure.tools.CreatePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomStageCardRepositoryImpl implements CustomStageCardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StageCard> customFindMethod(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StageCard> cq = cb.createQuery(StageCard.class);
        Root<StageCard> stage = cq.from(StageCard.class);
        cq.select(stage);

        List<Predicate> predicates = CreatePredicate.<StageCard>createEventOrStagePredicates(params, cb, stage);

        if (predicates.isEmpty()) {
            return new ArrayList<>();
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).getResultList();
    }
}
