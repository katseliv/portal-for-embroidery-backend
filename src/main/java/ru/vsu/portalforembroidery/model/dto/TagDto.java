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
@JsonDeserialize(builder = TagDto.TagDtoBuilder.class)
public class TagDto {

    @NotNull(message = "Title is null.")
    @NotBlank(message = "Title is blank.")
    private final String title;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TagDtoBuilder {

    }

}
