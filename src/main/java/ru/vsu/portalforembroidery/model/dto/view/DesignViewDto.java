package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DesignViewDto {

    private final Integer id;
    private final String name;
    private final String base64StringFile;
    private final String creatorDesignerFirstName;
    private final String creatorDesignerLastName;

}
