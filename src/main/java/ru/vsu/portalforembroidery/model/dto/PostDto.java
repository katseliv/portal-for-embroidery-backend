package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = PostDto.PostDtoBuilder.class)
public class PostDto {

    @NotNull(message = "Designer Id is null.")
    @Positive(message = "Designer Id is negative or zero.")
    private final Integer designerId;

    @NotNull(message = "Design Id is null.")
    @Positive(message = "Design Id is negative or zero.")
    private final Integer designId;

    @NotNull(message = "Description is null.")
    @NotBlank(message = "Description is blank.")
    private final String description;

    @NotNull(message = "Files is null.")
    private final List<FileDto> files;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PostDtoBuilder {

    }

}
