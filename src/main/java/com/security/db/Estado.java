package com.security.db;


import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "estado")
@Data
public class Estado {

    @Id
    @Column(name = "esta_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_esta")
    @SequenceGenerator(name = "seq_esta", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "esta_nombre", unique = true)
    @NotBlank
    @NotEmpty
    private String nombre;

    @Column(name = "esta_tipo")
    @NotBlank
    @NotEmpty
    private String tipo; 

}
