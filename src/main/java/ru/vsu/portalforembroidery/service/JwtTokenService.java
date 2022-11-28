package ru.vsu.portalforembroidery.service;

import ru.vsu.portalforembroidery.model.JwtTokenType;
import ru.vsu.portalforembroidery.model.dto.JwtTokenDto;

public interface JwtTokenService {

    void createJwtToken(JwtTokenDto jwtTokenDto);

    String getJwtTokenByEmailAndType(String email, JwtTokenType type);

    boolean existsByToken(String token);

    boolean existsByUserEmail(String email);

    void updateJwtToken(JwtTokenDto jwtTokenDto);

    void deleteJwtTokensByEmail(String email);

}
