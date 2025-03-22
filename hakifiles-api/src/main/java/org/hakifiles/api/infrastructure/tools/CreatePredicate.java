package org.hakifiles.api.infrastructure.tools;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
}
