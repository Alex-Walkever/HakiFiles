package org.hakifiles.api.domain.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class SearchMapper {
//    public static Specification<CharacterCard>  toSearchCharacterDto(Map<String, String> params) {
//        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher("cost ");
//        Specification<CharacterCard> specification = Specification.where()
//        return specification;
//    }
}
