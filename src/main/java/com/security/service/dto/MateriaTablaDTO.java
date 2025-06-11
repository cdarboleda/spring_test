package com.security.service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MateriaTablaDTO {
    private Integer id;

    private String nombre;

    private Integer periodo;

    private Integer horas;

    private Integer maestriaId;

    private String maestriaNombre;

    private String maestriaCodigo;

    private Integer maestriaCohorte;

    private LocalDate maestriaFechaEjecucionDesde;

    private LocalDate maestriaFechaEjecucionHasta;

    public MateriaTablaDTO(){}
    public MateriaTablaDTO(
            Integer id,
            String nombre,
            Integer periodo,
            Integer horas,
            Integer maestriaId,
            String maestriaNombre,
            String maestriaCodigo,
            Integer maestriaCohorte,
            LocalDate maestriaFechaEjecucionDesde,
            LocalDate maestriaFechaEjecucionHasta) {
                this.id = id;
                this.nombre = nombre;
                this.periodo = periodo;
                this.horas = horas;
                this.maestriaId = maestriaId;
                this.maestriaNombre = maestriaNombre;
                this.maestriaCodigo = maestriaCodigo;
                this.maestriaCohorte = maestriaCohorte;
                this.maestriaFechaEjecucionDesde = maestriaFechaEjecucionDesde;
                this.maestriaFechaEjecucionHasta = maestriaFechaEjecucionHasta;

    }
}