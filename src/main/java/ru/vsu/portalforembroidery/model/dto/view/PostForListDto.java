package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostForListDto {

    private final Integer id;
    private final String designName;
    private final String designBase64StringImage;
    private final String description;
    private final Integer countLikes;
    private final boolean liked;

}