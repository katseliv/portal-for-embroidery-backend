package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostViewDto {

    private final Integer id;
    private final String designerFirstName;
    private final String designerLastName;
    private final String designName;
    private final String designBase64StringFile;
    private final String description;
    private final String creationDatetime;

}