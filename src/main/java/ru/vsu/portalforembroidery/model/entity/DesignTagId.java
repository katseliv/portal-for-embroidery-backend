package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DesignTagId implements Serializable {

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "design_id")
    private Integer designId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DesignTagId likeId = (DesignTagId) o;
        return tagId.equals(likeId.tagId) && designId.equals(likeId.designId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, designId);
    }

}
