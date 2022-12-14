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
@JsonDeserialize(builder = DesignFileDto.DesignFileDtoBuilder.class)
public class DesignFileDto {

    @NotNull(message = "Design Id is null.")
    @Positive(message = "Design Id is negative or zero.")
    private final Integer designId;

    @NotNull(message = "File Id is null.")
    @Positive(message = "File Id is negative or zero.")
    private final Integer fileId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignFileDtoBuilder {

    }

}
