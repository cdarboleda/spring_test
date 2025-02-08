package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.exception.CustomException;
import com.security.factory.PasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.utils.ConvertidorPaso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorPasoServiceImpl implements IGestorPasoService {

    @Autowired
    private IPasoRepository pasoRepository;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private ConvertidorPaso convertidorPaso;

    @Autowired
    private IPasoService pasoService;

    @Autowired
    private PasoFactoryManager factoryManager;

    @Override
    public List<PasoDTO> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }
    
    @Override
    public Paso updatePasoResponsable(Integer idPaso, Integer idResponsable) {
        Paso paso = this.pasoService.findById(idPaso);
        Persona responsable = this.personaService.findById(idResponsable);

        // if(responsable.activo!=null){
        //     paso.setResponsable(responsable);
        // }
        //el filtro de rol de un responsable le hacemos en el front
        if(responsable.getRoles().contains(paso.getRol())){
            paso.setResponsable(responsable);
        }else{
            throw new RuntimeException("El responsable no tiene el rol");
        }

        return paso;
    }

    // inserta un paso con responsable y proceso
    // el orden no verifica
    @Override
    public Paso insertarUnico(PasoDTO pasoDTO) {
        Paso paso = new Paso();
        if (this.pasoService.findByIdOptional(pasoDTO.getId()).isPresent()) {
            throw new CustomException("Ya existe un paso con ese id: " + pasoDTO.getId(),
                    HttpStatus.BAD_REQUEST);
        }

        convertidorPaso.convertirAEntidad(paso, pasoDTO);
        Persona persona = this.personaService.findById(pasoDTO.getIdResponsable());
        Proceso proceso = this.procesoService.findById(pasoDTO.getIdProceso());
        paso.setProceso(proceso);
        paso.setResponsable(persona);
        return this.pasoRepository.save(paso);
    }

    // @Override
    // public List<Paso> insertarMultiple(List<PasoDTO> pasosDTO, Integer idProceso)
    // {

    // Proceso proceso = this.procesoService.findById(idProceso);
    // Map<Integer, Persona> personasMapa = new HashMap<>();
    // List<Paso> pasosInsertados= new ArrayList<>();
    // pasosDTO.stream().forEach((pasoDTO)->{
    // Paso paso = new Paso();
    // convertidorPaso.convertirAEntidad(paso, pasoDTO);
    // if(!personasMapa.containsKey(pasoDTO.getIdResponsable())){
    // Persona persona = this.personaService.findById(pasoDTO.getIdResponsable());
    // personasMapa.put(pasoDTO.getIdResponsable(), persona);
    // paso.setResponsable(persona);
    // }else{
    // paso.setResponsable(personasMapa.get(pasoDTO.getIdResponsable()));
    // }
    // paso.setProceso(proceso);
    // pasosInsertados.add(this.pasoRepository.save(paso));

    // });

    // return pasosInsertados;

    // }

    // es el que guarda los pasos de golpe al inicio de crear un proceso
    @Override
    public List<Paso> insertarMultipleAProceso(List<PasoDTO> pasosDTO, Integer idProceso) {
        // Verificar que el proceso exista
        Proceso proceso = this.procesoService.findById(idProceso);
        // Convertir y guardar los pasos
        List<Paso> pasosInsertados = pasosDTO.stream()
                .map(pasoDTO -> {
                    Paso paso = new Paso();
                    convertidorPaso.convertirAEntidad(paso, pasoDTO);
                    // paso.setResponsable(null);
                    paso.setProceso(proceso);
                    return this.pasoRepository.save(paso);
                })
                .collect(Collectors.toList());

        return pasosInsertados;
    }

    // porsiacaso, si ya tuvieran responsable y necesitase insertarlos de golpe
    @Override
    public List<Paso> insertarMultipleConResponsable(List<PasoDTO> pasosDTO, Integer idProceso) {
        // Verificar que el proceso exista
        Proceso proceso = this.procesoService.findById(idProceso);

        // Extraer IDs únicos de responsables
        Set<Integer> idsResponsables = pasosDTO.stream()
                .map(PasoDTO::getIdResponsable)
                .collect(Collectors.toSet());

        // Validar que todos los IDs de responsables existan
        Map<Integer, Persona> personasMapa = idsResponsables.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> this.personaService.findById(id) // Lanza excepción si un ID no existe
                ));

        // Convertir y guardar los pasos
        List<Paso> pasosInsertados = pasosDTO.stream()
                .map(pasoDTO -> {
                    Paso paso = new Paso();
                    convertidorPaso.convertirAEntidad(paso, pasoDTO);
                    paso.setResponsable(personasMapa.get(pasoDTO.getIdResponsable()));
                    paso.setProceso(proceso);
                    return this.pasoRepository.save(paso);
                })
                .collect(Collectors.toList());

        return pasosInsertados;
    }

}
