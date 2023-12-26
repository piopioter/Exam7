package com.example.personinfo.people.utils;

import com.example.personinfo.people.models.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DynamicSpecification {

    public static Specification<Person> byCriteria(Map<String, String> criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            criteria.keySet()
                    .stream()
                    .filter(key -> key.endsWith("From"))
                    .forEach(key -> {
                        String baseKey = key.substring(0, key.length() - 4);
                        if (criteria.containsKey(baseKey + "To")) {
                            String fromValue = criteria.get(key);
                            String toValue = criteria.get(baseKey + "To");
                            handleRangeCriteria(root, builder, predicates, baseKey, fromValue, toValue);
                        }
                    });
            criteria.forEach((key, value) -> {
                if (!key.endsWith("From") && !key.endsWith("To"))
                    handleStringCriteria(root, builder, predicates, key, value);
            });

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


    private static void handleRangeCriteria(Root<Person> root, CriteriaBuilder builder, List<Predicate> predicates,
                                            String field, String fromValue, String toValue) {
        predicates.add(builder.between(root.get(field), fromValue, toValue));

    }

    private static void handleStringCriteria(Root<Person> root, CriteriaBuilder
            builder, List<Predicate> predicates, String key, String value) {
        predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
    }
}



