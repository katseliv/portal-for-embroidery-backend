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
@JsonDeserialize(builder = ModelPhotoDto.ModelPhotoDtoBuilder.class)
public class ModelPhotoDto {

    @NotNull(message = "File is null.")
    private final String base64StringFile;

    @NotNull(message = "Design Id is null.")
    @Positive(message = "Design Id is negative or zero.")
    private final Integer designId;

    @NotNull(message = "Placement Position Id is null.")
    @Positive(message = "Placement Position Id is negative or zero.")
    private final Integer placementPositionId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ModelPhotoDtoBuilder {

    }

}
