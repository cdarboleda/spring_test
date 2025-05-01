package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.CarpetaDocumento;
import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.ProcesoPagoDocente;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.Estado;
import com.security.db.enums.Evento;
import com.security.db.enums.TipoProceso;
import com.security.exception.CustomException;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.repo.IPasoRepository;
import com.security.repo.IProcesoLogRepository;
import com.security.repo.IProcesoPagoDocenteRepository;
import com.security.repo.IProcesoRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IGestorProcesoService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.IRolService;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPagoDocenteDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.utils.ConvertidorCarpetaDocumento;
import com.security.service.dto.utils.ConvertidorPaso;
import com.security.service.dto.utils.ConvertidorPersona;
import com.security.service.dto.utils.ConvertidorProceso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoImpl implements IGestorProcesoService {

    private static final Boolean FINALIZADO_ESTADO_INICIAL = false;
    private static final LocalDateTime FECHA_FIN_INICIAL = null;

    @Autowired
    private IProcesoRepository procesoRepository;

    @Autowired
    private IProcesoService procesoService;
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private ICarpetaDocumentoRepository carpetaDocumentoRepository;

    @Autowired
    private IRolService rolService;

    @Autowired
    private IPasoRepository pasoRepository;
    @Autowired
    private IProcesoLogRepository procesoLogRepository;
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
    private IProcesoPagoDocenteRepository procesoPagoDocenteRepository;
    @Autowired
    private IProcesoTitulacionRepository procesoTitulacionRepository;

    @Autowired
    private IGestorProcesoLogService gestorProcesoLogService;

    @Autowired
    private ConvertidorPaso convertidorPaso;

    @Override
    public List<ProcesoDTO> findProcesosByRequirienteId(Integer id) {
        return findEntitiesByPersonId(id, this.procesoRepository::findByRequirienteId,
                "procesos relacionados a la persona");
    }

    public List<ProcesoDTO> findEntitiesByPersonId(Integer id, Function<Integer, List<Proceso>> procesoFetcher,
            String tipoConsulta) {
        this.personaService.existsById(id);
        List<Proceso> procesos = procesoFetcher.apply(id);
        if (procesos.isEmpty())
            throw new EntityNotFoundException("No se encontraron " + tipoConsulta + " para persona con id: " + id);
        List<ProcesoDTO> procesosLigeros = procesos.stream()
                .map(convertidorProceso::convertirALigeroDTO)
                .collect(Collectors.toList());
        return procesosLigeros;
    }

    // @SuppressWarnings("unchecked")
    private Object insertarProcesoEspecifico(Proceso proceso, ProcesoDTO procesoDTO) {

        if (procesoDTO.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE.toString())) {
            ProcesoPagoDocenteDTO procesoPDDTO = (ProcesoPagoDocenteDTO) procesoDTO;
            ProcesoPagoDocente pagoDocente = new ProcesoPagoDocente();
            proceso.setDescripcion("En este proceso se realiza el trámite de pago para un docente");
            pagoDocente.setProceso(proceso);
            pagoDocente.setModalidadVirtual(procesoPDDTO.getModalidadVirtual());
            return procesoPagoDocenteRepository.save(pagoDocente);
        } else if (procesoDTO.getTipoProceso().equals(TipoProceso.TITULACION.toString())) {
            ProcesoTitulacionDTO procesoTDTO = (ProcesoTitulacionDTO) procesoDTO;
            ProcesoTitulacion titulacion = new ProcesoTitulacion();
            proceso.setDescripcion("En este proceso se realiza el trámite titulación de un estudiante de maestría");
            titulacion.setProceso(proceso);
            titulacion.setGrupo(procesoTDTO.getGrupo());
            titulacion.setCalificacionFinal(procesoTDTO.getCalificacionFinal());
            titulacion.setFechaDefensa(procesoTDTO.getFechaDefensa());
            titulacion.setNotaLector1(procesoTDTO.getNotaLector1());
            titulacion.setNotaLector2(procesoTDTO.getNotaLector2());

            // Agrega los compañeros al proceso en caso de ser modo GRUPAL
            /*
             * if (procesoTDTO.getGrupo() != null && procesoTDTO.getIdInvolucrados() !=
             * null) {
             * asociarCompañerosAlProceso(proceso, procesoTDTO.getIdInvolucrados());
             * 
             * }
             */
            return this.procesoTitulacionRepository.save(titulacion);
        }

        return null;
    }

    private void asociarCompañerosAlProceso(Proceso proceso, List<Integer> idsCompañeros) {

        // Asegura que el requiriente también sea parte del de Involucrados
        if (!idsCompañeros.contains(proceso.getRequiriente().getId())) {
            idsCompañeros.add(proceso.getRequiriente().getId());
        }

        // Validar que los IDs de los compañeros

        List<Persona> compañeros = idsCompañeros.stream()
                .map(this.personaService::findById)
                .collect(Collectors.toList());

        // Asociar cada compañero al proceso con pasos y carpetas
        for (Persona compañero : compañeros) {

            CarpetaDocumento carpeta = new CarpetaDocumento();
            // carpeta.setPersona(compañero);
            carpeta.setProceso(proceso);
            carpeta.setUrl("test"); // resolver este campo
            this.carpetaDocumentoRepository.save(carpeta);

        }

    }

    // Método para insertar un nuevo registro de Proceso
    @Override
    public Object insert(ProcesoDTO procesoDTO) {

        Proceso proceso = new Proceso();
        proceso.setFinalizado(FINALIZADO_ESTADO_INICIAL);
        proceso.setFechaFin(FECHA_FIN_INICIAL);
        proceso.setFechaInicio(LocalDateTime.now());
        proceso.setTipoProceso(TipoProceso.valueOf(procesoDTO.getTipoProceso()));
        Persona requiriente = this.personaService.findById(procesoDTO.getRequirienteId());
        proceso.setRequiriente(requiriente);

        List<Paso> pasos = this.gestorPasoService.crearPasos(procesoDTO.getTipoProceso())
                .stream()
                .map(pasoDTO -> {
                    Paso paso = new Paso();
                    convertidorPaso.convertirAEntidad(paso, pasoDTO);
                    paso.setProceso(proceso);
                    paso.setRol(this.rolService.buscarPorNombre(pasoDTO.getRol()).get());

                    return paso;
                }).collect(Collectors.toList());

        pasos.get(0).setResponsable(requiriente);
        if (procesoDTO.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE.toString())) {
            pasos.stream().filter(
                    paso -> paso.getNombre() == "documentacion_docente" || paso.getNombre() == "factura_docente")
                    .forEach((paso -> paso.setResponsable(requiriente)));
        } else if (procesoDTO.getTipoProceso().equals(TipoProceso.TITULACION.toString())) {
            pasos.stream().filter(paso -> paso.getNombre() == "registro_propuesta")
                    .forEach((paso -> paso.setResponsable(requiriente)));

        }

        proceso.setPasos(pasos);

        this.procesoRepository.save(proceso);
        var procesoEspecifico = this.insertarProcesoEspecifico(proceso, procesoDTO);
        // guarda los logs de creacion del paso (PENDIENTE)
        pasos.forEach(paso -> gestorProcesoLogService.insertarProcesoLog(paso, Evento.CREACION));
        return convertidorProceso.convertirACompletoDTO(procesoEspecifico);
    }

    @Override
    public ProcesoDTO update(ProcesoDTO procesoDTO) {

        Proceso proceso = this.procesoService.findById(procesoDTO.getId());
        proceso.setFechaFin(procesoDTO.getFechaFin());
        proceso.setFinalizado(true);
        proceso.setCancelado(procesoDTO.getCancelado());
        return convertidorProceso.convertirALigeroDTO(proceso);
    }

    // Me toco borrar uno a uno sin el cascade all sino se iba alv
    // aaaaaaasdasdasdasd
    @Override
    public void delete(Integer id) {
        this.procesoPagoDocenteRepository.deleteById(id);
        this.procesoTitulacionRepository.deleteById(id);
        carpetaDocumentoRepository.deleteByProcesoId(id);
        pasoRepository.deleteByProcesoId(id);
        procesoLogRepository.deleteByProcesoId(id);

        this.procesoRepository.deleteById(id);
    }

    @Override
    public Object findByIdCompletoDTO(Integer id) {
        Optional<ProcesoPagoDocente> pago = this.procesoPagoDocenteRepository.findById(id);
        Optional<ProcesoTitulacion> titulacion = this.procesoTitulacionRepository.findById(id);
        Object proceso;
        if (pago.isPresent()) {
            proceso = pago.get();
        } else if (titulacion.isPresent()) {
            proceso = titulacion.get();
        } else {
            throw new IllegalArgumentException("findByIdCompletoDTO Tipo de proceso desconocido");
        }
        return convertidorProceso.convertirACompletoDTO(proceso);
    }

    @Override
    public List<MiProcesoDTO> findMisProcesos() {
        return this.procesoRepository.findMisProcesos();
    }

    // devuelve la lista de MiProceso en procesos donde soy responsable de un
    // proceso
    // en el caso del coordinador si se le mostraría,
    // funciona siempre y cuando sea responsable de almenos un paso,
    // si es que hubiera un proceso en el que el requiriente no interviene en el
    // programa como responsable
    // no es suficiente, el metodo anterior me daria la info
    // pero en el flujo normal, suele ser ambos, asi que toca añadir logica si es
    // que en el futuro
    // se llega a esos casos, por el momento no es necesario
    @Override
    public List<MiProcesoDTO> findMisProcesosByResponsableId(Integer responsableId) {
        return this.procesoRepository.findMisProcesosByResponsableId(responsableId);
    }

    public List<MiProcesoDTO> obtenerMisProcesos(Integer responsableId) {
        List<Proceso> procesos = procesoRepository.findProcesosByResponsableId(responsableId);

        // Crear una lista de DTOs para devolver
        List<MiProcesoDTO> resultado = new ArrayList<>();

        for (Proceso proceso : procesos) {
            // Buscar el paso en estado EN_CURSO, si existe
            Paso pasoEnCurso = proceso.getPasos().stream()
                    .filter(p -> p.getEstado() == Estado.EN_CURSO && p.getResponsable().getId().equals(responsableId))
                    .findFirst()
                    .orElseGet(() ->
                    // Si no se encuentra un paso con el responsableId, obtener el primer paso en
                    // EN_CURSO
                    proceso.getPasos().stream()
                            .filter(p -> p.getEstado() == Estado.EN_CURSO)
                            .findFirst()
                            .orElse(null));

            // Crear el DTO con la información relevante
            MiProcesoDTO dto = new MiProcesoDTO(
                    proceso.getId(),
                    proceso.getTipoProceso(),
                    proceso.getFechaInicio(),
                    proceso.getFechaFin(),
                    proceso.getFinalizado(),
                    proceso.getCancelado(),
                    proceso.getRequiriente().getId(),
                    proceso.getRequiriente().getCedula(),
                    proceso.getRequiriente().getNombre(),
                    proceso.getRequiriente().getApellido(),
                    pasoEnCurso != null ? pasoEnCurso.getNombre() : null,
                    pasoEnCurso != null ? pasoEnCurso.getEstado().toString() : null,
                    pasoEnCurso != null ? pasoEnCurso.getDescripcionEstado() : null,
                    pasoEnCurso != null && pasoEnCurso.getResponsable() != null ? pasoEnCurso.getResponsable().getId()
                            : null,
                    pasoEnCurso != null && pasoEnCurso.getResponsable() != null
                            ? pasoEnCurso.getResponsable().getCedula()
                            : null);

            // Agregar a la lista de resultados
            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public List<ProcesoPasoDocumentoDTO> obtenerDetalleProcesoId(Integer procesoId) {
        return this.procesoRepository.findProcesoDetalleById(procesoId);
    }

}
