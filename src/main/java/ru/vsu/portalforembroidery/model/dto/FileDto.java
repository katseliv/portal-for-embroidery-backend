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
@JsonDeserialize(builder = FileDto.FileDtoBuilder.class)
public class FileDto {

    @NotNull(message = "File Name is null.")
    @NotBlank(message = "File Name is blank.")
    private final String name;

    @NotNull(message = "File Extension is null.")
    @NotBlank(message = "File Extension is blank.")
    private final String extension;

    @NotNull(message = "File is null.")
    @NotBlank(message = "File is blank.")
    private final String base64StringFile;

    @Positive(message = "Design Id is negative or zero.")
    private final Integer designId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FileDtoBuilder {

    }

}
