package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ModelPhotoForListDto {

    private final Integer id;
    private final String designName;
    private final BigDecimal placementPositionHeightRelativeSize;
    private final BigDecimal placementPositionWidthRelativeSize;

}
