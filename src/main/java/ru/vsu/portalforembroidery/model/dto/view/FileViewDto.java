package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileViewDto {

    private final Integer id;
    private final String name;
    private final String extension;
    private final String base64StringFile;

}
