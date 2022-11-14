package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserViewDto {

    private final Integer id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String base64StringImage;
    private final String phoneNumber;
    private final String email;

}
