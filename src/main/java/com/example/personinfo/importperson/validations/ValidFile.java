package com.example.personinfo.importperson.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {ValidFileValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,ElementType.TYPE_USE, ElementType.TYPE, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {

    String message() default "Invalid type file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
