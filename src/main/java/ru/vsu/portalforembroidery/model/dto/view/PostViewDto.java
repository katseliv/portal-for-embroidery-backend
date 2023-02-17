package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

import java.util.List;

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
    private final List<String> designBase64StringImages;
    private final String description;
    private final String creationDatetime;
    private final Integer countLikes;

}