package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.vsu.portalforembroidery.annotation.DatetimeValid;
import ru.vsu.portalforembroidery.annotation.ImageValid;

import javax.validation.constraints.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = DesignerProfileDto.DesignerProfileDtoBuilder.class)
public class DesignerProfileDto {

    @NotNull(message = "Username is null.")
    @NotBlank(message = "Username is blank.")
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

    @ImageValid
    private final String base64StringImage;

    @NotNull(message = "Phone Number is null.")
    @NotBlank(message = "Phone Number is blank.")
    @Pattern(regexp = "^([0-9])+$", message = "Phone Number mustn't contain a letter.")
    @Size(min = 7, max = 11, message = "Phone Number is out of range {7, 11}.")
    private final String phoneNumber;

    @NotNull(message = "Email is null.")
    @NotBlank(message = "Email is blank.")
    @Email(message = "Email invalid.")
    private final String email;

    @DatetimeValid
    private final String experiencedSince;

    @NotNull(message = "Description is null.")
    @NotBlank(message = "Description is blank.")
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignerProfileDtoBuilder {

    }

}
