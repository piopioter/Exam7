package com.example.personinfo.people.utils;

import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class GenericSpecificationsBuilder<T> {

    private Specification<T> spec;

    public GenericSpecificationsBuilder() {
        this.spec = Specification.where(null);
    }

    public GenericSpecificationsBuilder<T> hasType(String entityType) {
        if (entityType != null) {
            try {
                Class<?> entityClass = Class.forName("com.example.personinfo.people.models." + entityType);
                spec = spec.and((root, query, builder) ->
                        builder.equal(root.type(), builder.literal(entityClass)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasFirstName(String firstName) {
        if (firstName != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasLastName(String lastName) {
        if (lastName != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasPesel(String pesel) {
        if (pesel != null) {
            spec = spec.and((root, query, builder) ->
                    builder.equal(root.get("pesel"), pesel));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasHeightBetween(Double min, Double max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("height"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasWeightBetween(Double min, Double max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("weight"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasEmail(String email) {
        if (email != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasCurrentPosition(String currentPosition) {
        if (currentPosition != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("currentPosition")), "%" + currentPosition.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasCurrentSalaryBetween(Double min, Double max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("currentSalary"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasEmploymentDateBetween(LocalDate min, LocalDate max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("employmentDate"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasStudyYearBetween(LocalDate min, LocalDate max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("studyYear"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasStudyField(String studyField) {
        if (studyField != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("studyField")), "%" + studyField.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasUniversityName(String universityName) {
        if (universityName != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("universityName")), "%" + universityName.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasScholarshipAmountBetween(Double min, Double max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("scholarshipAmount"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasPensionAmountBetween(Double min, Double max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("pensionAmount"), min, max));
        }
        return this;
    }

    public GenericSpecificationsBuilder<T> hasYearsWorkedBetween(Integer min, Integer max) {
        if (min != null && max != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("yearsWorked"), min, max));
        }
        return this;
    }

    public Specification<T> build() {
        return spec;
    }




}
