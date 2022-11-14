package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DesignerDesignViewDto {

    private final String designerFirstName;
    private final String designerLastName;
    private final String designName;
    private final String designBase64StringFile;
    private final String permission;

}
