package com.security.db;

import java.time.LocalDateTime;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proceso_log")
@Data
public class ProcesoLog {

    @Id
    @Column(name = "proc_log_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc_log")
    @SequenceGenerator(name = "seq_proc_log", initialValue = 1, allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "proc_id")
    private Proceso proceso;

    @Column(name = "proc_log_descripcion")
    private String descripcion;

    @Column(name = "proc_log_fecha_cambio")
    private LocalDateTime fechaCambio;

    @Column(name = "proc_log_tipo_evento")
    private String tipoEvento;
    
}
