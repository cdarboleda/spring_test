package com.security.db;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.db.enums.TipoProceso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "proceso")
@Data
public class Proceso {

    //nombre, descripcion, titulacion
    //estado -> finalizado
    //pago docente a OneToOne
    public Proceso(){

    }

    @Id
    @Column(name = "proc_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc")
    @SequenceGenerator(name = "seq_proc", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "proc_fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "proc_fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "proc_finalizado")
    private Boolean finalizado;// = false;

    @Column(name = "proc_cancelado")
    private Boolean cancelado;// = false;


    @Column(name = "proc_descripcion") 
    private String descripcion;

    @Column(name = "proc_tipo_proceso", nullable = true)//si es nulo es un generico
    private TipoProceso tipoProceso;
    
    @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<CarpetaDocumento> carpetasDocumento;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
        @ToString.Exclude
    @JoinColumn(name = "pers_id")
    private Persona requiriente;

    // @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JsonIgnore
    // private List<ProcesoLog> procesoLog;

    @OneToMany(mappedBy = "proceso", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @ToString.Exclude
    private List<Paso> pasos; 
}

