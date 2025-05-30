package com.security.db.enums.converter;

import com.security.db.enums.Rol;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolConverter implements AttributeConverter<Rol, String> {
    @Override
    public String convertToDatabaseColumn(Rol rol) {
        if (rol == null) {
            return null;
        }
        return rol.getNombre();
    }

    @Override
    public Rol convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (Rol rol : Rol.values()) {
            if (rol.getNombre().equals(dbData)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol desconocido: " + dbData);
    }


}
