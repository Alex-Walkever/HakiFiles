package org.hakifiles.api.domain.repositories.card.category.character;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hakifiles.api.infrastructure.tools.CreatePredicate.createIntPredicate;

public class CustomCharacterCardRepositoryImpl implements CustomCharacterCardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CharacterCard> customFindMethod(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CharacterCard> cq = cb.createQuery(CharacterCard.class);
        Root<CharacterCard> character = cq.from(CharacterCard.class);
        cq.select(character);

        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("name")) {
            String name = params.get("name");
            if (name != null) {
                predicates.add(cb.like(character.get("name"), "%" + name + "%"));
            }
        }
        if (params.containsKey("type")) {
            String type = params.get("type");
            if (type != null) {
                List<String> types = new ArrayList<>(Arrays.stream(type.split(",")).toList());
                
                Join<CharacterCard, String> joinType = character.join("type");
                predicates.add(cb.and(joinType.in(types)));
            }
        }
        if (params.containsKey("effects")) {
            String effects = params.get("effects");
            if (effects != null) {
                predicates.add(cb.like(character.get("effects"), "%" + effects + "%"));
            }
        }
        if (params.containsKey("triggerEffect")) {
            String triggerEffect = params.get("triggerEffect");
            if (triggerEffect != null) {
                predicates.add(cb.like(character.get("triggerEffect"), "%" + triggerEffect + "%"));
            }
        }
        if (params.containsKey("cost")) {
            String costString = params.get("cost");
            if (costString != null) {
                Predicate predicate = createIntPredicate("cost", costString, cb, character);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        if (params.containsKey("power")) {
            String powerString = params.get("power");
            if (powerString != null) {
                Predicate predicate = createIntPredicate("power", powerString, cb, character);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        if (params.containsKey("counterPower")) {
            String counterPowerString = params.get("counterPower");
            if (counterPowerString != null) {
                Predicate predicate = createIntPredicate("counterPower", counterPowerString, cb, character);
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
                Join<CharacterCard, CardInfo.Attribute> joinType = character.join("attribute");
                predicates.add(cb.and(joinType.in(attributes)));
            }
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).getResultList();
    }
}
