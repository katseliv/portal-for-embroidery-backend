package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserForListDto {

    private final Integer id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;

}
