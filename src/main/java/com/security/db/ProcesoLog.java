package com.security.db;

import java.time.LocalDateTime;

import com.security.db.enums.Evento;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proc_log")
    @SequenceGenerator(name = "seq_proc_log", initialValue = 1, allocationSize = 1)
    private Integer id;

    // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JsonIgnore
    // @JoinColumn(name = "proc_id")
    // private Proceso proceso;
    @Column(name = "proc_log_proceso_id")
    private Integer procesoId;

    @Column(name = "proc_log_responsable_id")
    private Integer responsableId;

    @Column(name = "proc_log_responsable_cedula")
    private String responsableCedula;

    @Column(name = "proc_log_responsable_nombre")
    private String responsableNombre;

    // @Column(name = "proc_log_paso_id")
    // private Integer pasoId;

    @Column(name = "proc_log_paso_orden")
    private Integer pasoOrden;

    @Column(name = "proc_log_paso_nombre")
    private String pasoNombre;

    @Column(name = "proc_log_paso_estado")
    private String pasoEstado;

    @Column(name = "proc_log_tipo_evento")
    private Evento tipoEvento;

    @Column(name = "proc_log_paso_estado_descripcion")
    private String pasoEstadoDescripcion;

    @Column(name = "proc_log_observacion")
    private String pasoObservacion;

    @Column(name = "proc_log_fecha_cambio")
    private LocalDateTime fechaCambio;

}
