package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlacementPositionViewDto {

    private final Integer id;
    private final String name;
    private final Integer anchor;
    private final BigDecimal topMarginPosition;
    private final BigDecimal bottomMarginPosition;
    private final BigDecimal leftMarginPosition;
    private final BigDecimal rightMarginPosition;
    private final BigDecimal heightPercent;
    private final BigDecimal widthPercent;

}
