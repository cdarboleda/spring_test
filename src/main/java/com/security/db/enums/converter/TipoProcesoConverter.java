package com.security.db.enums.converter;

import com.security.db.enums.TipoProceso;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoProcesoConverter implements AttributeConverter<TipoProceso, String>{
    @Override
    public String convertToDatabaseColumn(TipoProceso tipoProceso) {
        if (tipoProceso == null) {
            return null;
        }
        return tipoProceso.name();
    }

    @Override
    public TipoProceso convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoProceso.valueOf(dbData);
    }
}
