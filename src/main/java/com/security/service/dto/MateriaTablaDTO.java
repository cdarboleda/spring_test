package com.security.service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MateriaTablaDTO {
    private Integer id;

    private String codigo;

    private String nombre;

    private Integer periodo;

    private Integer maestriaId;

    private String maestriaNombre;

    private String maestriaCodigo;

    private String maestriaCohorte;

    private LocalDate maestriaFechaEjecucionDesde;

    private LocalDate maestriaFechaEjecucionHasta;

    public MateriaTablaDTO(){}
    public MateriaTablaDTO(
            Integer id,
            String codigo,
            String nombre,
            Integer periodo,
            Integer maestriaId,
            String maestriaNombre,
            String maestriaCodigo,
            String maestriaCohorte,
            LocalDate maestriaFechaEjecucionDesde,
            LocalDate maestriaFechaEjecucionHasta) {
                this.id = id;
                this.codigo = codigo;
                this.nombre = nombre;
                this.periodo = periodo;
                this.maestriaId = maestriaId;
                this.maestriaNombre = maestriaNombre;
                this.maestriaCodigo = maestriaCodigo;
                this.maestriaCohorte = maestriaCohorte;
                this.maestriaFechaEjecucionDesde = maestriaFechaEjecucionDesde;
                this.maestriaFechaEjecucionHasta = maestriaFechaEjecucionHasta;

    }
}