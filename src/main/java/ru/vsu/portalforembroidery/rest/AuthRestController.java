package ru.vsu.portalforembroidery.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.portalforembroidery.model.dto.UserDetailsDto;
import ru.vsu.portalforembroidery.model.request.JwtRequest;
import ru.vsu.portalforembroidery.model.request.LoginRequest;
import ru.vsu.portalforembroidery.model.response.JwtResponse;
import ru.vsu.portalforembroidery.model.response.LoginResponse;
import ru.vsu.portalforembroidery.service.AuthService;
import ru.vsu.portalforembroidery.utils.SecurityContextFacade;
import springfox.documentation.annotations.ApiIgnore;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@ApiIgnore
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthRestController {

    private final AuthService authService;
    private final SecurityContextFacade securityContextFacade;
    protected AuthenticationManager authenticationManager;

    @PostMapping("/accessToken")
    public ResponseEntity<LoginResponse> accessToken(@RequestBody @Valid final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        securityContextFacade.getContext().setAuthentication(authentication);

        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final LoginResponse loginResponse = authService.login(userDetailsDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/newAccessToken")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody @Valid final JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.getNewAccessToken(jwtRequest.getRefreshToken());
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        final UserDetailsDto userDetailsDto = (UserDetailsDto) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final String email = userDetailsDto.getEmail();
        authService.logout(email);
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

}
