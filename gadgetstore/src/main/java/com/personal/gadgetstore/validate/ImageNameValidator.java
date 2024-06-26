package com.personal.gadgetstore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {
    Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("ImageNameValid aspect is executed");
        if (value.contains(".") && value.length() > 4) {
            return true;
        }
        return false;
    }
}
