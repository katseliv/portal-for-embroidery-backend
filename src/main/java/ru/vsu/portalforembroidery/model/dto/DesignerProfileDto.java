package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.vsu.portalforembroidery.annotation.DatetimeValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = DesignerProfileDto.DesignerProfileDtoBuilder.class)
public class DesignerProfileDto {

    @DatetimeValid
    private final String experiencedSince;

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignerProfileDtoBuilder {

    }

}
