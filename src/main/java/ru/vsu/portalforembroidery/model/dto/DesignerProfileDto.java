package ru.vsu.portalforembroidery.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.vsu.portalforembroidery.annotation.DatetimeValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DesignerProfileDto extends UserDto {

    @NotNull(message = "Designer Id is null.")
    @Positive(message = "Designer Id is negative ot zero.")
    private final Integer designerId;

    @DatetimeValid
    private final String experiencedSince;

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String description;

}
