package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DesignFileViewDto {

    private final Integer id;
    private final String designName;

}
