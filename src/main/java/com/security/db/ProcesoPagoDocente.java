package com.security.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "proceso_pago_docente")
@Data
public class ProcesoPagoDocente {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "proc_id")
    @JsonIgnore
    private Proceso proceso;

    @Column(name = "pago_doce_modalidad_virtual")
    private Boolean modalidadVirtual;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "mate_id")
    private Materia materia;

    @Column(name = "pago_doce_fecha_inicio_clase")
    private LocalDate fechaInicioClase;

    @Column(name = "pago_doce_fecha_fin_clase")
    private LocalDate fechaFinClase;

    // @Column(name = "pago_doce_cohorte")
    // private Integer cohorte;

}
