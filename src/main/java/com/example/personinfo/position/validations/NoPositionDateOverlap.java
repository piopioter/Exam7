package com.example.personinfo.position.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {NoPositionDateOverlapValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,ElementType.TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoPositionDateOverlap {

    String message() default "{Position date  overlaps with existing position}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
