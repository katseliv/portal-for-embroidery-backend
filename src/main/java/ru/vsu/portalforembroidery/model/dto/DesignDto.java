package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = DesignDto.DesignDtoBuilder.class)
public class DesignDto {

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String name;

    @NotNull(message = "Creator Designer Id is null.")
    @Positive(message = "Creator Designer Id is negative or zero.")
    private final Integer creatorDesignerId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignDtoBuilder {

    }

}
