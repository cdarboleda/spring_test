package com.security.db;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "proceso_titulacion")
@Data
public class ProcesoTitulacion {

    @Id
    private Integer id;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "proc_id")
    // @JsonIgnore
    // private Proceso proceso;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "proc_id")
    @JsonIgnore
    private Proceso proceso;

    @Nullable
    @Column(name = "titu_grupo")
    private Boolean grupo;

    @Column(name = "titu_calificacion_final")
    private Double calificacionFinal;

    @Column(name = "titu_fecha_defensa")
    private LocalDateTime fechaDefensa;

    @Column(name = "titu_nota_tribunal_1")
    private Double notaTribunal1;

    @Column(name = "titu_persona_tribunal_1")
    private String personaTribunal1;

    @Column(name = "titu_nota_tribunal_2")
    private Double notaTribunal2;

    @Column(name = "titu_nota_tribunal_3")
    private Double notaTribunal3;

    @Column(name = "titu_persona_tribunal_2")
    private String personaTribunal2;

    @Column(name = "titu_nota_lector_1")
    private Double notaLector1;

    @Column(name = "titu_nota_lector_2")
    private Double notaLector2;

    @Column(name = "titu_tutor_id")
    private Integer tutorId;

    @OneToMany(mappedBy = "procesoTitulacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ObservacionLector> observaciones;
}
