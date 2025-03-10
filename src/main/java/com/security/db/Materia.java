package com.security.db;

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

@Entity
@Table(name = "materia")
@Data
public class Materia {
 
    @Id
    @Column(name = "mate_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_mate")
    @SequenceGenerator(name = "seq_mate", initialValue = 1, allocationSize = 1)
    private Integer id;
 
    @Column(name = "mate_codigo_maestria")
    private String codigoMaestria;
 
    @Column(name = "mate_nombre_maestria")
    private String nombreMaestria;
 
    @Column(name = "mate_codigo_materia")
    private String codigoMateria;
 
    @Column(name = "mate_nombre_materia")
    private String nombreMateria;
 
    @Column(name = "mate_numero_horas")
    private Integer numeroHoras;
 
    @Column(name = "mate_cohorte")
    private String cohorte;
 
    //relacion con proceso pago
    // @OneToMany(mappedBy = "materia",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JsonIgnore
    // private List<Proceso> procesos;
   
}