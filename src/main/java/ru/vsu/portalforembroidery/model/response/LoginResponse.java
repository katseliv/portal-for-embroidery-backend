package ru.vsu.portalforembroidery.model.response;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LoginResponse {

    private final Integer id;
    private final String accessToken;
    private final String refreshToken;
    private final Integer expiresAt;

}
