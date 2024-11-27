package com.security.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "proc_log_proc_id")
    private Integer idProceso;

    @Column(name = "proc_log_proc_paso_id")
    private Integer idProcesoPaso;

    @Column(name = "proc_log_pers_id")
    private Integer idResponsable;
    
    @Column(name = "proc_log_paso_nombre")
    private String pasoNombre;

    @Column(name = "proc_log_estado")
    private String estado;

    @Column(name = "proc_log_comentario")
    private String comentario;

    @Column(name = "proc_log_fecha_cambio")
    private LocalDateTime fechaCambio;
    
}
