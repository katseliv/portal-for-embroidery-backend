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
@JsonDeserialize(builder = FolderDto.FolderDtoBuilder.class)
public class FolderDto {

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String name;

    @Positive(message = "Parent Folder Id is negative or zero.")
    private final Integer parentFolderId;

    @NotNull(message = "Creator Designer Id is null.")
    @Positive(message = "Creator Designer Id is negative or zero.")
    private final Integer creatorDesignerId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FolderDtoBuilder {

    }

}
