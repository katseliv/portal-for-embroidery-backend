package ru.vsu.portalforembroidery.model.dto.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class ViewListPage<T> {

    protected final int pageNumber;
    protected final int pageSize;
    protected final int totalPages;
    protected final List<T> viewDtoList;

}
