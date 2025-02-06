package com.security.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "rol")
@Data
public class Rol {

    @Id
    @Column(name = "rol_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol")
    @SequenceGenerator(name = "seq_rol", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "rol_nombre", unique = true)
    private String nombre;

    @Column(name = "rol_descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Persona> personas = new HashSet<>();   

    //Me toco poner estos, sino los set se dañan
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        return id != null && id.equals(((Rol) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
