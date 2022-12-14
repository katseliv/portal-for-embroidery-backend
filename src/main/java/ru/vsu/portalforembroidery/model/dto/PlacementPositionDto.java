package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = PlacementPositionDto.PlacementPositionDtoBuilder.class)
public class PlacementPositionDto {

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String name;

    @NotNull(message = "Anchor is null.")
    @Min(value = 0, message = "Anchor is negative.")
    @Max(value = 16, message = "Anchor must be less than 16.")
    private final Integer anchor;

    @NotNull(message = "Top Margin Position is null.")
    @Positive(message = "Top Margin Position is negative or zero.")
    private final BigDecimal topMarginPosition;

    @NotNull(message = "Bottom Margin Position is null.")
    @Positive(message = "Bottom Margin Position is negative or zero.")
    private final BigDecimal bottomMarginPosition;

    @NotNull(message = "Left Margin Position is null.")
    @Positive(message = "Left Margin Position is negative or zero.")
    private final BigDecimal leftMarginPosition;

    @NotNull(message = "Right Margin Position is null.")
    @Positive(message = "Right Margin Position is negative or zero.")
    private final BigDecimal rightMarginPosition;

    @NotNull(message = "Height Relative Size is null.")
    @Min(value = 0, message = "Height Relative Size is negative.")
    @Max(value = 1, message = "Height Relative Size must be less than 1.")
    private final BigDecimal heightRelativeSize;

    @NotNull(message = "Width Relative Size is null.")
    @Min(value = 0, message = "Width Relative Size is negative.")
    @Max(value = 1, message = "Width Relative Size must be less than 1.")
    private final BigDecimal widthRelativeSize;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlacementPositionDtoBuilder {

    }

}
