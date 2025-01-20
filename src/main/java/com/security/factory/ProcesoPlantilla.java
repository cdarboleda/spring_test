package com.security.factory;

import org.springframework.stereotype.Component;

import com.security.db.enums.TipoProceso;

import lombok.Data;

@Data
@Component
public class ProcesoPlantilla {
    protected String nombre;
    protected String descripcion;
    protected TipoProceso tipoProceso;
}
