package com.security.db;

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
@Table(name = "proceso_paso")
@Data
public class ProcesoPaso {

    @Id
    @Column(name = "proc_paso_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc_paso")
    @SequenceGenerator(name = "seq_proc_paso", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "proc_paso_nombre")
    private String nombre;

    @Column(name = "proc_paso_descripcion")
    private String descripcion;

    @Column(name = "proc_paso_orden")
    private Integer orden;

    @Column(name = "proc_comentario")
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="proc_id")
    private Proceso proceso;

    //UN paso tiene un estado, pero varios estados pueden compartir un estado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esta_id", nullable = false)
    private Estado estado;

}
