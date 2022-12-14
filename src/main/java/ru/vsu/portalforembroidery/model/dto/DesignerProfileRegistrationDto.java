package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.vsu.portalforembroidery.annotation.DatetimeValid;
import ru.vsu.portalforembroidery.annotation.PasswordMatch;
import ru.vsu.portalforembroidery.annotation.PasswordValid;
import ru.vsu.portalforembroidery.annotation.UsernameValid;

import javax.validation.constraints.*;

@PasswordMatch
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = DesignerProfileRegistrationDto.DesignerProfileRegistrationDtoBuilder.class)
public class DesignerProfileRegistrationDto {

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
    @Positive(message = "Role Id is negative or zero.")
    private final Integer roleId;

    @NotNull(message = "Password is null.")
    @NotBlank(message = "Password is blank.")
    @PasswordValid
    private final String password;

    @NotNull(message = "Password Confirmation is null.")
    @NotBlank(message = "Password Confirmation is blank.")
    @PasswordValid
    private final String passwordConfirmation;

    @DatetimeValid
    private final String experiencedSince;

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignerProfileRegistrationDtoBuilder {

    }

}
