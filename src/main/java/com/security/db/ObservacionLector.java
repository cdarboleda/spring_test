package com.security.db;

import java.util.List;

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
@Table(name = "observacion_lector")
@Data
public class ObservacionLector {

    @Id
    @Column(name = "obse_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_obse")
    @SequenceGenerator(name = "seq_obse", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "obse_lector_num")
    private Integer lectorNumero;

    @Column(name = "obse_observacion")
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="titu_id")
    @JsonIgnore
    private ProcesoTitulacion procesoTitulacion;

}
