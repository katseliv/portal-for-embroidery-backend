package ru.vsu.portalforembroidery.model.dto.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DesignerProfileViewDto extends UserViewDto {

    private final String experiencedSince;
    private final String description;

}
