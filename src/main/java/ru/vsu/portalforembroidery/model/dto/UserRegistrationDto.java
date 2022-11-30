package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.vsu.portalforembroidery.annotation.PasswordMatch;
import ru.vsu.portalforembroidery.annotation.PasswordValid;
import ru.vsu.portalforembroidery.annotation.UsernameValid;

import javax.validation.constraints.*;

@PasswordMatch
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = UserRegistrationDto.UserRegistrationDtoBuilder.class)
public class UserRegistrationDto {

    @NotNull(message = "Username is null.")
    @NotBlank(message = "Username is blank.")
    @UsernameValid
    @Pattern(regexp = ".*([A-Z]|[a-z]|[А-Я]|[а-я]).*", message = "Username must contain a letter.")
    private final String username;

    @NotNull(message = "First Name is null.")
    @NotBlank(message = "First Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z]|[А-Я]|[а-я])+$", message = "First Name mustn't contain a number.")
    private final String firstName;

    @NotNull(message = "Last Name is null.")
    @NotBlank(message = "Last Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z]|[А-Я]|[а-я])+$", message = "Last Name mustn't contain a number.")
    private final String lastName;

    @NotNull(message = "Phone Number is null.")
    @NotBlank(message = "Phone Number is blank.")
    @Pattern(regexp = "^([0-9])+$", message = "Phone Number mustn't contain a letter.")
    @Size(min = 7, max = 11, message = "Phone Number is out of range {7, 11}.")
    private final String phoneNumber;

    @NotNull(message = "Email is null.")
    @NotBlank(message = "Email is blank.")
    @Email(message = "Email invalid.")
    private final String email;

    @NotNull(message = "Role Id is null.")
    @Positive(message = "Role Id is negative ot zero.")
    private final Integer roleId;

    @NotNull(message = "Password is null.")
    @NotBlank(message = "Password is blank.")
    @PasswordValid
    private final String password;

    @NotNull(message = "Password Confirmation is null.")
    @NotBlank(message = "Password Confirmation is blank.")
    @PasswordValid
    private final String passwordConfirmation;

}
