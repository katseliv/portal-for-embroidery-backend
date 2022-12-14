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
@JsonDeserialize(builder = DesignerDesignDto.DesignerDesignDtoBuilder.class)
public class DesignerDesignDto {

    @NotNull(message = "Designer Id is null.")
    @Positive(message = "Designer Id is negative or zero.")
    private final Integer designerId;

    @NotNull(message = "Design Id is null.")
    @Positive(message = "Design Id is negative or zero.")
    private final Integer designId;

    @NotNull(message = "Permission Id is null.")
    @Positive(message = "Permission Id is negative or zero.")
    private final Integer permissionId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DesignerDesignDtoBuilder {

    }

}
