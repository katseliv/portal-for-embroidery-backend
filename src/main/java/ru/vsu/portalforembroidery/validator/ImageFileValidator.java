package ru.vsu.portalforembroidery.validator;

import org.springframework.beans.factory.annotation.Value;
import ru.vsu.portalforembroidery.annotation.ImageValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ImageValid, String> {

    @Value("${image.limitSize}")
    private int limitSize;

    @Override
    public boolean isValid(final String base64StringImage, final ConstraintValidatorContext context) {
        final byte[] bytes = Base64.getDecoder().decode(base64StringImage);
        final int imageSize = bytes.length;

        final List<String> messages = new ArrayList<>();
        if (base64StringImage == null) {
            messages.add("Image File is null.");
        }

        if (imageSize > limitSize) {
            messages.add("Image File is too big.");
        }

        final String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return messages.size() == 0;
    }

}
