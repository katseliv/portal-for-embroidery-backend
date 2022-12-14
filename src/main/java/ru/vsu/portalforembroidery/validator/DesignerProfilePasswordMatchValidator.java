package ru.vsu.portalforembroidery.validator;

import ru.vsu.portalforembroidery.annotation.PasswordMatch;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DesignerProfilePasswordMatchValidator implements ConstraintValidator<PasswordMatch, DesignerProfileRegistrationDto> {

    @Override
    public boolean isValid(final DesignerProfileRegistrationDto designerProfileRegistrationDto, final ConstraintValidatorContext context) {
        final String password = designerProfileRegistrationDto.getPassword();
        final String passwordConfirmation = designerProfileRegistrationDto.getPasswordConfirmation();
        return password != null && password.equals(passwordConfirmation);
    }

}
