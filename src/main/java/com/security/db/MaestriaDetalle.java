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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "maestria_detalle")
@Data
public class MaestriaDetalle {

    @Id
    @Column(name = "maes_deta_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_maes_deta")
    @SequenceGenerator(name = "seq_maes_deta", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "maes_deta_cohorte")
    private String cohorte;

    @Column(name = "maes_fecha_inicio_ejecucion")
    private LocalDateTime fechaInicio;

    @Column(name = "maes_fecha_fin_ejecucion")
    private LocalDateTime fechaFin;

    @Column(name = "maes_deta_estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "maes_id")
    private Maestria maestria;

    @OneToMany(mappedBy = "maestriaDetalle",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Materia> materias;
    
}
