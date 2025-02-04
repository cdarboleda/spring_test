package com.security.db;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@Table(name = "carpeta_documento")
@Data
public class CarpetaDocumento {

    @Id
    @Column(name = "carp_docu_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_carp_docu")
    @SequenceGenerator(name = "seq_carp_docu", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name ="carp_docu_url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="proc_id")
    @JsonIgnore
    private Proceso proceso;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name="pers_id")
    // @JsonIgnore
    // private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="paso_id")
    @JsonIgnore
    private Paso paso;

}
