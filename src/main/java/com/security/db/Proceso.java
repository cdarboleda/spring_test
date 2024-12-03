package com.security.db;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proceso")
@Data
public class Proceso {

    @Id
    @Column(name = "proc_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc")
    @SequenceGenerator(name = "seq_proc", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "proc_nombre")
    private String nombre;

    @Column(name = "proc_descripcion")
    private String descripcion;

    @Column(name = "proc_fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "proc_fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "proc_estado")
    private Boolean estado;
    
    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Documento> documentos;

    //Es el de id_owner del proceso
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "pers_id")
    private Persona persona;

    //Es la de procesoPersonas
    @ManyToMany(mappedBy = "personasProceso", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Persona> personas;

    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProcesoPaso> procesoPasos; 

}

