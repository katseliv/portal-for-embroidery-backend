package ru.vsu.portalforembroidery.annotation;

import ru.vsu.portalforembroidery.validator.DesignerProfilePasswordMatchValidator;
import ru.vsu.portalforembroidery.validator.UserPasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserPasswordMatchValidator.class, DesignerProfilePasswordMatchValidator.class})
public @interface PasswordMatch {

    String message() default "Passwords aren't the same.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
