package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.Paso;
import com.security.exception.CustomException;
import com.security.factory.ProcesoFactory;
import com.security.factory.ProcesoPlantilla;
import com.security.repo.IProcesoRepository;
import com.security.service.IGestorProceso;
import com.security.service.IPersonaService;
import com.security.service.IPasoService;
import com.security.service.IProcesoService;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoLigeroDTO;
import com.security.service.dto.utils.ConvertidorDocumento;
import com.security.service.dto.utils.ConvertidorPersona;
import com.security.service.dto.utils.ConvertidorProceso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoImpl implements IGestorProceso{

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
    private ConvertidorDocumento convertidorDocumento;
    @Autowired
    private IPasoService pasoService;

    @Override
    public List<ProcesoLigeroDTO> findProcesosByPersonaId(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findByPersonaId, "procesos relacionados a la persona");
    }
    @Override
    public List<ProcesoLigeroDTO> findProcesosWherePersonaIsOwner(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findByPersonasId, "procesos del requiriente");
    }
       
    public List<ProcesoLigeroDTO> findEntitiesByPersonId(Integer id,Function<Integer, List<Proceso>> procesoFetcher,
        String tipoConsulta) {
        this.personaService.existsById(id);
        List<Proceso> procesos = procesoFetcher.apply(id);
        if(procesos.isEmpty()) throw new EntityNotFoundException("No se encontraron "+tipoConsulta+" para persona con id: "+id);
        List<ProcesoLigeroDTO> procesosLigeros = procesos.stream()
            .map(convertidorProceso::convertirALigeroDTO)
            .collect(Collectors.toList());
        return procesosLigeros;
    }
    

    @Override
    public ProcesoLigeroDTO insert(ProcesoDTO procesoDTO) {
        if (procesoDTO == null) throw new CustomException("Error en los campos enviados", HttpStatus.BAD_REQUEST);

        ProcesoPlantilla procesoTipoPlantilla = procesoFactory.createProceso(procesoDTO.getNombre());
        Persona requiriente = this.personaService.findById(procesoDTO.getRequirienteId());
        List<Persona> personasDelProceso = this.personaService.findPersonasByIds(procesoDTO.getPersonasId());

        List<Paso> pasos = this.pasoService.crearPasos(procesoDTO.getNombre());


        Proceso proceso = new Proceso();
        proceso.setNombre(procesoTipoPlantilla.getNombre());
        proceso.setDescripcion(procesoTipoPlantilla.getDescripcion());
        proceso.setFechaInicio(LocalDateTime.now());
        proceso.setEstado(false);
        proceso.setPersona(requiriente);

        //Agregar El Proceso a cada paso
        pasos.forEach((paso) -> paso.setProceso(proceso));

        //AgregarPasos
        proceso.setPasos(pasos);


        personasDelProceso.forEach((persona)->proceso.addPersona(persona));

        Proceso procesoGuardado = this.procesoRepository.save(proceso);
        return convertidorProceso.convertirALigeroDTO(procesoGuardado);
    }

    //le deje en el gestor ya que talvez luego toque usarel de personas
    @Override
    public ProcesoLigeroDTO update(ProcesoDTO procesoDTO) {
        if (procesoDTO == null) throw new CustomException("Error en los campos enviados", HttpStatus.BAD_REQUEST);
        Proceso proceso = this.procesoService.findById(procesoDTO.getId());
        proceso.setFechaFin(procesoDTO.getFechaFinal());
        proceso.setEstado(procesoDTO.getEstado());
        return convertidorProceso.convertirALigeroDTO(proceso);
    }
    
    @Override
    public void delete(Integer id){
        Proceso proceso = this.procesoService.findById(id);
        // proceso.getPersonas().forEach(persona -> {
        //     persona.getPersonasProceso().remove(proceso);
        // });
        // proceso.getPersonas().clear();
        procesoRepository.deleteById(id);
    }

    @Override
    public ProcesoCompletoDTO findByIdCompletoDTO(Integer id){
        Proceso proceso = this.procesoService.findById(id);
        ProcesoCompletoDTO procesoDTO = convertidorProceso.convertirACompletoDTO(proceso);

        procesoDTO.setDocumentos(
            proceso.getDocumentos().stream()
            .map(convertidorDocumento::convertirALigeroDTO)
            .collect(Collectors.toList())
        );
        procesoDTO.setRequiriente(convertidorPersona.convertirALigeroDTO(proceso.getPersona()));
        procesoDTO.setPersonasProceso(
            proceso.getPersonas().stream()
                .map(convertidorPersona::convertirALigeroDTO)
                .collect(Collectors.toCollection(HashSet::new))
        );
        return procesoDTO;
    }
    
}
