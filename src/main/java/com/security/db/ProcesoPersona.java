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


//////////////////////////////Borrar eso

@Entity
@Table(name="proceso_persona")
@Data
public class ProcesoPersona {
    @Id
    @Column(name = "proc_pers_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc_pers")
    @SequenceGenerator(name = "seq_proc_pers", initialValue = 1, allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "proc_id")
    private Proceso proceso;

    @ManyToOne
    @JoinColumn(name = "pers_id")
    private Persona persona;
}
