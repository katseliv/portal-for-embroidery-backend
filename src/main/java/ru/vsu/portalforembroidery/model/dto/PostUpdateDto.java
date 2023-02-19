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
@JsonDeserialize(builder = PostUpdateDto.PostUpdateDtoBuilder.class)
public class PostUpdateDto {

    @NotNull(message = "Description is null.")
    @NotBlank(message = "Description is blank.")
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PostUpdateDtoBuilder {

    }

}
