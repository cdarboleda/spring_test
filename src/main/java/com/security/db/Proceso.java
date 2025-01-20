package com.security.db;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.db.enums.TipoProceso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proceso")
@Data
public class Proceso {

    //nombre, descripcion, titulacion
    //estado -> finalizado
    //pago docente a OneToOne

    @Id
    @Column(name = "proc_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_proc")
    @SequenceGenerator(name = "seq_proc", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "proc_fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "proc_fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "proc_estado")
    private Boolean finalizado;

    @Column(name = "proc_nombre") //Se sobrescriben en los hijos, si no, pues al crear como generico se pone cualquier cosa
    private String nombre;
    @Column(name = "proc_descripcion") 
    private String descripcion;

    @Column(name = "paso_tipo_proceso", nullable = true)//si es nulo es un generico
    private TipoProceso tipoProceso;
    
    @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CarpetaDocumento> carpetasDocumento;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "pers_id")
    private Persona requiriente;

    @OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProcesoLog> procesoLog;

    // @ManyToMany(mappedBy = "procesoTitulacionPersonas", fetch = FetchType.LAZY)
    // @JsonIgnore
    // private Set<Persona> personas = new HashSet<>();

    //Se usa para añadir persona
    // public void addPersona(Persona persona){
    //     this.personas.add(persona);
    //     persona.getProcesoTitulacionPersonas().add(this);
    // }

    //Este no se usa
    // public void removePersona(Persona persona){
    //     this.personas.remove(persona);
    //     persona.getPersonasProceso().remove(this);
    // }

    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paso> pasos; 

    @OneToOne
    @JoinColumn(name =" proc_id", unique = true)
    @JsonIgnore
    private ProcesoPagoDocente procesoPagoDocente;

    @OneToOne
    @JoinColumn(name =" proc_id", unique = true)
    @JsonIgnore
    private ProcesoTitulacion procesoTitulacion;

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (!(o instanceof Proceso)) return false;
    //     return id != null && id.equals(((Proceso) o).getId());
    // }
 
    // @Override
    // public int hashCode() {
    //     return getClass().hashCode();
    // }

}

