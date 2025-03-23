package org.hakifiles.api.infrastructure.tools;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CreatePredicate {
    public static Predicate createIntPredicate(String key, String operationAndValue,
                                               CriteriaBuilder cb, Root<?> root) {
        String operation = operationAndValue.substring(0, 2);
        if (operation.equals("eq")) {
            return cb.equal(root.get(key), Integer.parseInt(operationAndValue.substring(2)));
        }
        if (operation.equals("gt")) {
            return cb.gt(root.get(key), Integer.parseInt(operationAndValue.substring(2)));
        }
        if (operation.equals("ge")) {
            return cb.ge(root.get(key), Integer.parseInt(operationAndValue.substring(2)));
        }
        if (operation.equals("lt")) {
            return cb.lt(root.get(key), Integer.parseInt(operationAndValue.substring(2)));
        }
        if (operation.equals("le")) {
            return cb.le(root.get(key), Integer.parseInt(operationAndValue.substring(2)));
        }
        return null;
    }

    public static <T> List<Predicate> createEventOrStagePredicates(Map<String, String> params, CriteriaBuilder cb, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("name")) {
            String name = params.get("name");
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
        }
        if (params.containsKey("type")) {
            String type = params.get("type");
            if (type != null) {
                List<String> types = new ArrayList<>(Arrays.stream(type.split(",")).toList());

                Join<T, String> joinType = root.join("type");
                predicates.add(cb.and(joinType.in(types)));
            }
        }
        if (params.containsKey("effects")) {
            String effects = params.get("effects");
            if (effects != null) {
                predicates.add(cb.like(root.get("effects"), "%" + effects + "%"));
            }
        }
        if (params.containsKey("triggerEffect")) {
            String triggerEffect = params.get("triggerEffect");
            if (triggerEffect != null) {
                predicates.add(cb.like(root.get("triggerEffect"), "%" + triggerEffect + "%"));
            }
        }
        if (params.containsKey("cost")) {
            String costString = params.get("cost");
            if (costString != null) {
                Predicate predicate = createIntPredicate("cost", costString, cb, root);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }

        return predicates;
    }
}
