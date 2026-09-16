package com.example.hcnstore_backend.core.annotation;

import com.example.hcnstore_backend.core.annotation.impl.FileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNotEmpty {
    String message() default "File is empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
