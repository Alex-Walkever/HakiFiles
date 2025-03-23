package org.hakifiles.api.domain.repositories.card.category.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hakifiles.api.domain.entities.card.category.EventCard;
import org.hakifiles.api.infrastructure.tools.CreatePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomEventCardRepositoryImpl implements CustomEventCardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventCard> customFindMethod(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventCard> cq = cb.createQuery(EventCard.class);
        Root<EventCard> event = cq.from(EventCard.class);
        cq.select(event);

        List<Predicate> predicates = CreatePredicate.<EventCard>createEventOrStagePredicates(params, cb, event);

        if (predicates.isEmpty()) {
            return new ArrayList<>();
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).getResultList();
    }
}
