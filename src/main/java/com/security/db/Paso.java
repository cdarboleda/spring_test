package com.security.db;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "paso")
@Data
public class Paso {

    @Id
    @Column(name = "paso_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paso")
    @SequenceGenerator(name = "seq_paso", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "paso_nombre")
    private String nombre;

    @Column(name = "paso_orden")
    private Integer orden;

    @Column(name = "paso_fecha_inicio")
    private LocalDateTime fechaInicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proceso_id")
    @ToString.Exclude
    @JsonIgnore
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    @JsonIgnore
    private Persona responsable;

    // UN paso tiene un estado, pero varios estados pueden compartir un estado
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    @ToString.Exclude
    private Estado estado;
}
