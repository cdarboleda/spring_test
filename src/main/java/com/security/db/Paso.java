package com.security.db;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.type.EnumType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.db.enums.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "paso")
@Data
public class Paso {

    @Id
    @Column(name = "paso_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paso")
    @SequenceGenerator(name = "seq_paso", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "paso_nombre", nullable = false)
    private String nombre;

    @Column(name = "paso_orden", nullable = false)
    private Integer orden;

    @Column(name = "paso_descripcion_paso", nullable = false)
    private String descripcionPaso;

    @Column(name = "paso_descripcion_estado")
    private String descripcionEstado;

    @Column(name = "paso_fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "paso_estado", nullable = false)
    private Estado estado;

    @Column(name = "paso_fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "paso_observacion")
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proc_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers_id")
    @JsonIgnore
    private Persona responsable;

    @OneToMany(mappedBy = "paso", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnore
    private List<CarpetaDocumento> carpetaDocumentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonIgnore
    private Rol rol;
}
