package com.security.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "documento")
@Data
public class Documento {

    @Id
    @Column(name = "docu_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_docu")
    @SequenceGenerator(name = "seq_docu", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "docu_nombre")
    private String nombre;

    @Column(name ="docu_url")
    private String url;

    @Column(name = "docu_descripcion")
    private String descripcion;

    @Column(name = "docu_fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name="proc_id")
    private Proceso proceso;

}
