package ru.vsu.portalforembroidery.validator;

import ru.vsu.portalforembroidery.annotation.PasswordMatch;
import ru.vsu.portalforembroidery.model.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegistrationDto> {

    @Override
    public boolean isValid(final UserRegistrationDto userRegistrationDto, final ConstraintValidatorContext context) {
        final String password = userRegistrationDto.getPassword();
        final String passwordConfirmation = userRegistrationDto.getPasswordConfirmation();
        return password != null && password.equals(passwordConfirmation);
    }

}
