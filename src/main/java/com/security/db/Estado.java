package com.security.db;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estado")
@Data
public class Estado {

    @Id
    @Column(name = "esta_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_esta")
    @SequenceGenerator(name = "seq_esta", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "esta_nombre")
    private String nombre;

    @Column(name = "esta_descripcion")
    private String descripcion;

    //Este podemos ignorar, el chat dice que si no hace falta ir desde Estado a Paso,
    // podemos ignorar y dejar el ManyToOne de Paso a Estado
    //@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //private List<ProcesoPaso> procesoPasos;

}