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
@JsonDeserialize(builder = FileUpdateDto.FileUpdateDtoBuilder.class)
public class FileUpdateDto {

    @NotNull(message = "File Name is null.")
    @NotBlank(message = "File Name is blank.")
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FileUpdateDtoBuilder {

    }

}
