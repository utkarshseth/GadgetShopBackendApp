package com.personal.gadgetstore.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ImageNameValidator.class})
public @interface ImageNameValid {
    String message() default "Invalid image name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
