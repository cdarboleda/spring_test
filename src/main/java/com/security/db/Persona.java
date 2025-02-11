package com.security.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "persona")
@Data
public class Persona {

    //proceso titulacion, password
    //persona -> requiriente
    //pasos set -> pasos list

    @Id
    @Column(name = "pers_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_pers")
    @SequenceGenerator(name = "seq_pers", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "pers_id_keycloak", unique = true)
    private String idKeycloak;

    @NotBlank
    @NotEmpty
    @Column(name = "pers_nombre")
    private String nombre;
    @Column(name = "pers_apellido", nullable = false)
    private String apellido;
    @Column(name = "pers_correo", nullable = false)
    private String correo;
    @Column(name = "pers_cedula", unique = true , nullable = false)
    private String cedula;
    @Column(name = "pers_telefono")
    private String telefono;
    @Column(name = "pers_activo")
    private Boolean activo;

    @OneToMany(mappedBy = "requiriente",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Proceso> procesos;

    @OneToMany(mappedBy = "responsable", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Paso> pasos;
    
    // @OneToMany(mappedBy = "persona",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JsonIgnore
    // private List<CarpetaDocumento> carpetaDocumentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "persona_rol", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "pers_id"), // Columna que referencia a Persona
        inverseJoinColumns = @JoinColumn(name = "rol_id") // Columna que referencia a Rol
    )
    @JsonIgnore
    private Set<Rol> roles = new HashSet<>();

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (!(o instanceof Persona)) return false;
    //     return id != null && id.equals(((Persona) o).getId());
    // }
 
    // @Override
    // public int hashCode() {
    //     return getClass().hashCode();
    // }


}
