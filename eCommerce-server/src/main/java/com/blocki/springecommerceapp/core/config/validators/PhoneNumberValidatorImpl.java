package com.blocki.springecommerceapp.core.config.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String> {

    @Override
    public void initialize(PhoneNumberValidator contactNumber) {

    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {

        return contactField != null && contactField.matches("[0-9]+")
                && (contactField.length() > 8) && (contactField.length() < 14);
    }

}
