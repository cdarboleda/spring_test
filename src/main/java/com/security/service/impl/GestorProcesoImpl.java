package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Materia;
import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.ProcesoPagoDocente;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.TipoProceso;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.repo.IPasoRepository;
import com.security.repo.IProcesoLogRepository;
import com.security.repo.IProcesoPagoDocenteRepository;
import com.security.repo.IProcesoRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IGestorProcesoService;
import com.security.service.IMateriaService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.IRolService;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.MiProcesoPagoDocenteDTO;
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

    @Autowired
    private IProcesoRepository procesoRepository;

    @Autowired
    private IProcesoService procesoService;
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private IMateriaService materiaService;
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

    @SuppressWarnings("unchecked")
    private Object insertarProcesoEspecifico(Proceso proceso, ProcesoDTO procesoDTO) {
        if (procesoDTO.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE.toString())) {
            ProcesoPagoDocenteDTO procesoPDDTO = (ProcesoPagoDocenteDTO) procesoDTO;
            ProcesoPagoDocente pagoDocente = new ProcesoPagoDocente();
            proceso.setDescripcion("En este proceso se realiza el trámite de pago para un docente");
            pagoDocente.setProceso(proceso);
            pagoDocente.setModalidadVirtual(procesoPDDTO.getModalidadVirtual());
            Materia materia = this.materiaService.findById(procesoPDDTO.getMateriaId());
            pagoDocente.setMateria(materia);
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
            return procesoTitulacionRepository.save(titulacion);
        }
        return null;
    }

    // Método para insertar un nuevo registro de Proceso
    @Override
    public Object insert(ProcesoDTO procesoDTO) {

        Proceso proceso = new Proceso();
        proceso.setFinalizado(false);
        proceso.setCancelado(false);
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

        // pasos.get(0).setResponsable(requiriente);
        if (procesoDTO.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE.toString())) {
            pasos.stream().filter(
                    paso -> paso.getNombre() == "documentacion_docente" || paso.getNombre() == "factura_docente"
                            || paso.getNombre() == "validacion_asistencia_docente")
                    .forEach((paso -> paso.setResponsable(requiriente)));
        } else if (procesoDTO.getTipoProceso().equals(TipoProceso.TITULACION.toString())) {
            pasos.stream().filter(paso -> paso.getNombre() == "titu_paso1")
                    .forEach((paso -> paso.setResponsable(requiriente)));
        }

        proceso.setPasos(pasos);
        this.procesoRepository.save(proceso);
        var procesoEspecifico = this.insertarProcesoEspecifico(proceso, procesoDTO);
        // pasos.forEach(paso -> gestorProcesoLogService.insertarProcesoLog(paso, Evento.CREACION));
        return convertidorProceso.convertirACompletoDTO(procesoEspecifico);
    }

    @Override
    public ProcesoDTO update(ProcesoDTO procesoDTO) {

        Proceso proceso = this.procesoService.findById(procesoDTO.getId());
        proceso.setFechaFin(procesoDTO.getFechaFin());
        proceso.setFinalizado(true);
        if (procesoDTO.getCancelado() != null) {
            proceso.setCancelado(procesoDTO.getCancelado());
        }

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
    public List<MiProcesoDTO> findMisProcesosGeneral() {
        return this.procesoRepository.findMisProcesosGeneral();
    }

    @Override
    public List<MiProcesoDTO> findMisProcesosGeneralPorResponsable(Integer responsableId) {
        return this.procesoRepository.findMisProcesosGeneralPorResponsable(responsableId);
    }

    @Override
    public List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocente() {
        return this.procesoRepository.findMisProcesosPagoDocente();
    }

    @Override
    public List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocentePorResponsable(Integer responsableId) {
        return this.procesoRepository.findMisProcesosPagoDocentePorResponsable(responsableId);
    }

    @Override
    public List<ProcesoPasoDocumentoDTO> obtenerDetalleProcesoId(Integer procesoId) {
        return this.procesoRepository.findProcesoDetalleById(procesoId);
    }

}
