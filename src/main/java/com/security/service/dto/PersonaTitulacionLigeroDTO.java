package com.security.service.dto;

import lombok.Data;

@Data
public class PersonaTitulacionLigeroDTO {

    private Integer idProceso;
    private Integer id;
    private String nombre;

    public PersonaTitulacionLigeroDTO() {
    }

    public PersonaTitulacionLigeroDTO(Integer id, String nombre) {

        this.id = id;
        this.nombre = nombre;
    }
}
