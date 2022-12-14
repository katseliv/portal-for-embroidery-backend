package ru.vsu.portalforembroidery.validator;

import org.springframework.beans.factory.annotation.Value;
import ru.vsu.portalforembroidery.annotation.ImageValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ImageValidator implements ConstraintValidator<ImageValid, String> {

    @Value("${image.limitSize}")
    private int limitSize;

    @Override
    public boolean isValid(final String base64StringImage, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (base64StringImage == null) {
            buildConstraintValidatorContext(context, "Image is null.");
            return false;
        }

        final byte[] bytes = Base64.getDecoder().decode(base64StringImage);
        final int imageSize = bytes.length;

        if (imageSize > limitSize) {
            messages.add("Image is too big.");
        }

        final String messageTemplate = String.join(", ", messages);
        buildConstraintValidatorContext(context, messageTemplate);
        return messages.size() == 0;
    }

    private void buildConstraintValidatorContext(final ConstraintValidatorContext context, final String messageTemplate) {
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
