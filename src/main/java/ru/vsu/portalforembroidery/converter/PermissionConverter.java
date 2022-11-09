package ru.vsu.portalforembroidery.converter;

import ru.vsu.portalforembroidery.model.Permission;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionConverter implements AttributeConverter<Permission, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Permission attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Permission convertToEntityAttribute(final Integer dbData) {
        return Permission.of(dbData).orElse(null);
    }

}
