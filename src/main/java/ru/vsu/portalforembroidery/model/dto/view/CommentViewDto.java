package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentViewDto {

    private final Integer id;
    private final String userFirstName;
    private final String userLastName;
    private final String text;
    private final String creationDatetime;

}
