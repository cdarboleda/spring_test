package com.security.db;

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
import lombok.ToString;

@Entity
@Table(name = "materia")
@Data
@ToString
public class Materia {
 
    @Id
    @Column(name = "mate_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_mate")
    @SequenceGenerator(name = "seq_mate", initialValue = 1, allocationSize = 1)
    private Integer id;
 
    @Column(name = "mate_codigo")
    private String codigo;
 
    @Column(name = "mate_nombre")
    private String nombre;
 
    @Column(name = "mate_periodo")
    private Integer periodo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    // @ToString.Exclude
    @JoinColumn(name = "maes_deta_id")
    private MaestriaDetalle maestriaDetalle;

   
}