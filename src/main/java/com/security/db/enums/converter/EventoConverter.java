package com.security.db.enums.converter;

import com.security.db.enums.Evento;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventoConverter implements AttributeConverter<Evento, String>{
    @Override
    public String convertToDatabaseColumn(Evento tipoEvento) {
        if (tipoEvento == null) {
            return null;
        }
        return tipoEvento.name();
    }

    @Override
    public Evento convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Evento.valueOf(dbData);
    }
}
