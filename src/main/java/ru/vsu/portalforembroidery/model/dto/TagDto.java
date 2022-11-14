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
@JsonDeserialize(builder = TagDto.TagDtoBuilder.class)
public class TagDto {

    @NotNull(message = "Title is null.")
    @NotBlank(message = "Title is blank.")
    private final String title;

    @NotNull(message = "Count is null.")
    @Positive(message = "Count is negative ot zero.")
    private final Integer count;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TagDtoBuilder {

    }

}
