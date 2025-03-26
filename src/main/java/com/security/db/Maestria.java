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

    @OneToMany(mappedBy = "maestria",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MaestriaDetalle> maestriasDetalle;
    
}
