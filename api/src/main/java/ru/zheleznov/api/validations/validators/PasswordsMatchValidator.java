package ru.zheleznov.api.validations.validators;

import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.api.validations.annotations.PasswordsMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, SignUpForm> {

    @Override
    public boolean isValid(SignUpForm signUpForm, ConstraintValidatorContext constraintValidatorContext) {
        return signUpForm.getPasswordAgain().equals(signUpForm.getPassword());
    }
}
