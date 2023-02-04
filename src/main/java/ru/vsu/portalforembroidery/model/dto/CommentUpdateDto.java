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
@JsonDeserialize(builder = CommentUpdateDto.CommentUpdateDtoBuilder.class)
public class CommentUpdateDto {

    @NotNull(message = "Text is null.")
    @NotBlank(message = "Text is blank.")
    private final String text;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CommentUpdateDtoBuilder {

    }

}
