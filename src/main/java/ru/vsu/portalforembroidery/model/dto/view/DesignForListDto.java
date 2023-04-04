package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DesignForListDto {

    private final Integer id;
    private final String name;

}
