package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.ProcesoPagoDocente;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.TipoProceso;
import com.security.factory.ProcesoFactory;
import com.security.factory.ProcesoPlantilla;
import com.security.repo.IProcesoRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorProcesoService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoLigeroDTO;
import com.security.service.dto.utils.ConvertidorCarpetaDocumento;
import com.security.service.dto.utils.ConvertidorPaso;
import com.security.service.dto.utils.ConvertidorPersona;
import com.security.service.dto.utils.ConvertidorProceso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoImpl implements IGestorProcesoService {

    @Autowired
    private IProcesoRepository procesoRepository;
    @Autowired
    private ProcesoFactory procesoFactory;
    @Autowired
    private IProcesoService procesoService;
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private ConvertidorProceso convertidorProceso;
    @Autowired
    private ConvertidorPersona convertidorPersona;
    @Autowired
    private ConvertidorCarpetaDocumento convertidorDocumento;
    @Autowired
    private IPasoService pasoService;
    @Autowired
    private IGestorPasoService gestorPasoService;

    @Autowired
    private ConvertidorPaso convertidorPaso;

    @Override
    public List<ProcesoLigeroDTO> findProcesosByPersonaId(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findByRequirienteId,
                "procesos relacionados a la persona");
    }
    // @Override
    // public List<ProcesoLigeroDTO> findProcesosWherePersonaIsOwner(Integer id) {
    // return findEntitiesByPersonId(id, this.procesoRepository::findByPersonasId,
    // "procesos del requiriente");
    // }

    public List<ProcesoLigeroDTO> findEntitiesByPersonId(Integer id, Function<Integer, List<Proceso>> procesoFetcher,
            String tipoConsulta) {
        this.personaService.existsById(id);
        List<Proceso> procesos = procesoFetcher.apply(id);
        if (procesos.isEmpty())
            throw new EntityNotFoundException("No se encontraron " + tipoConsulta + " para persona con id: " + id);
        List<ProcesoLigeroDTO> procesosLigeros = procesos.stream()
                .map(convertidorProceso::convertirALigeroDTO)
                .collect(Collectors.toList());
        return procesosLigeros;
    }

    @Override
    public ProcesoLigeroDTO insert(ProcesoDTO procesoDTO) {

        Proceso proceso = new Proceso();
        ProcesoPlantilla procesoTipoPlantilla = procesoFactory.createProceso(procesoDTO.getTipoProceso());
        proceso.setNombre(procesoTipoPlantilla.getNombre());
        proceso.setDescripcion(procesoTipoPlantilla.getDescripcion());
        proceso.setFechaInicio(LocalDateTime.now());
        proceso.setFinalizado(false);
        proceso.setRequiriente(this.personaService.findById(procesoDTO.getRequirienteId()));
        proceso.setTipoProceso(TipoProceso.valueOf(procesoDTO.getTipoProceso()));

        List<Paso> pasos = this.gestorPasoService.crearPasos(procesoDTO.getTipoProceso())
                .stream()
                .map(pasoDTO -> {
                    Paso paso = new Paso();
                    convertidorPaso.convertirAEntidad(paso, pasoDTO);
                    paso.setProceso(proceso);
                    return paso;
                })
                .collect(Collectors.toList());

        if (procesoDTO.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE.toString())) {
            ProcesoPagoDocente pagoDocente = new ProcesoPagoDocente();
            pagoDocente.setProceso(proceso);
            pagoDocente.setModalidadVirtual(procesoDTO.getModalidadVirtual());
            pasos.get(0).setResponsable(this.personaService.findById(procesoDTO.getResponsablePrimerPaso()));

            proceso.setProcesoPagoDocente(pagoDocente);
        } else if (procesoDTO.getTipoProceso().equals(TipoProceso.TITULACION.toString())) {
            ProcesoTitulacion titulacion = new ProcesoTitulacion();
            titulacion.setProceso(proceso);
            proceso.setProcesoTitulacion(titulacion);
        }

        proceso.setPasos(pasos);

        Proceso procesoGuardado = this.procesoRepository.save(proceso);
        return convertidorProceso.convertirALigeroDTO(procesoGuardado);
    }

    // le deje en el gestor ya que talvez luego toque usarel de personas
    @Override
    public ProcesoLigeroDTO update(ProcesoDTO procesoDTO) {

        Proceso proceso = this.procesoService.findById(procesoDTO.getId());
        proceso.setFechaFin(procesoDTO.getFechaFinal());
        // proceso.setFinalizado(procesoDTO.getEstado());
        return convertidorProceso.convertirALigeroDTO(proceso);
    }

    @Override
    public void delete(Integer id) {
        this.procesoService.findById(id);
        // proceso.getPersonas().forEach(persona -> {
        // persona.getPersonasProceso().remove(proceso);
        // });
        // proceso.getPersonas().clear();
        procesoRepository.deleteById(id);
    }

    @Override
    public ProcesoCompletoDTO findByIdCompletoDTO(Integer id) {
        return convertidorProceso.convertirACompletoDTO(this.procesoService.findById(id));
    }

}
