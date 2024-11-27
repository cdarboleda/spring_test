package com.security.db;

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
@Table(name="persona_rol")
@Data
public class PersonaRol {
    @Id
    @Column(name = "pers_rol_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_pers_rol")
    @SequenceGenerator(name = "seq_pers_rol", initialValue = 1, allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pers_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}
