package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = DesignUpdateDto.DesignUpdateDtoBuilder.class)
public class DesignUpdateDto {

    @NotNull(message = "Design Name is null.")
    @NotBlank(message = "Design Name is blank.")
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignUpdateDtoBuilder {

    }

}
