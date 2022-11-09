package ru.vsu.portalforembroidery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@AllArgsConstructor
public enum Permission {

    OWNER(1),
    EDITOR(2),
    READER(3);

    private final int id;

    public static Optional<Permission> of(final Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (final var value : Permission.values()) {
            if (value.id == id) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

}
