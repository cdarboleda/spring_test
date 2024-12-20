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
@Table(name = "proceso_pago_docente")
@Data
public class ProcesoPagoDocente {

    @Id
    @Column(name = "pago_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_pago")
    @SequenceGenerator(name = "seq_pago", initialValue = 1, allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proc_id")
    @JsonIgnore
    private Proceso proceso;

    @Column(name = "pago_coordinador_identificador")
    private Integer coordinador_id;

    @Column(name = "pago_modalidad_virtual")
    private Boolean modalidadVirtual;
    
}
