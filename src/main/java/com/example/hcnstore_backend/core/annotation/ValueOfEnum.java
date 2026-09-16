package com.example.hcnstore_backend.core.annotation;

import com.example.hcnstore_backend.core.annotation.impl.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default "must be a valid enum value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
