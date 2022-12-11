package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = FileDto.FileDtoBuilder.class)
public class FileDto {

    @NotNull(message = "File is null.")
    private final String base64StringFile;

    @NotNull(message = "Folder Id is null.")
    @Positive(message = "Folder Id is negative ot zero.")
    private final Integer folderId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FileDtoBuilder {

    }

}
