package com.security.db;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "maestria")
@Data
public class Maestria {

    @Id
    @Column(name = "maes_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_maes")
    @SequenceGenerator(name = "seq_maes", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "maes_nombre")
    private String nombre;

    @Column(name = "maes_codigo")
    private String codigo;

    @Column(name = "maes_cohorte")
    private Integer cohorte;

    @Column(name = "maes_fecha_inicio_ejecucion")
    private LocalDate fechaInicio;

    @Column(name = "maes_fecha_fin_ejecucion")
    private LocalDate fechaFin;

    @Column(name = "maes_estado")
    private String estado;

    @OneToMany(mappedBy = "maestria",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @ToString.Exclude
    private List<Materia> materias;
    
}
