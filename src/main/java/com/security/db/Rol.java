package com.security.db;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rol")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol")
    @SequenceGenerator(name = "seq_rol", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "rol_nombre")
    private String nombre;

    @Column(name = "rol_descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonaRol> personaRoles;
    
}