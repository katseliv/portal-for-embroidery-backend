package ru.vsu.portalforembroidery.utils;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.portalforembroidery.exception.ParseInputException;

@Slf4j
public class ParseUtils {

    public static Integer parsePositiveInteger(final String value) {
        final int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (final NumberFormatException exception) {
            log.error("Can't parse input value {}", value);
            throw new ParseInputException("Invalid input string. Can't parse to number.", exception);
        }

        if (intValue <= 0) {
            log.error("Input value {} isn't positive number.", intValue);
            throw new ParseInputException("Number isn't positive.");
        }
        return intValue;
    }

}
