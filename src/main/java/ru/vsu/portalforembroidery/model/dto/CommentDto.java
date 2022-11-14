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
@JsonDeserialize(builder = CommentDto.CommentDtoBuilder.class)
public class CommentDto {

    @NotNull(message = "Post Id is null.")
    @Positive(message = "Post Id is negative ot zero.")
    private final Integer postId;

    @NotNull(message = "User Id is null.")
    @Positive(message = "User Id is negative ot zero.")
    private final Integer userId;

    @NotNull(message = "Text is null.")
    @NotBlank(message = "Text is blank.")
    private final String text;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CommentDtoBuilder {

    }

}
