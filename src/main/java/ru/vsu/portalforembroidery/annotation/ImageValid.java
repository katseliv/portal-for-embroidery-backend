package ru.vsu.portalforembroidery.annotation;

import ru.vsu.portalforembroidery.validator.ImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface ImageValid {

    String message() default "Image file invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
