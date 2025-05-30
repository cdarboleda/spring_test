package com.security.service;

import java.util.List;

import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.MiProcesoPagoDocenteDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPagoDocenteResponsablesDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;

public interface IGestorProcesoService {
    public List<ProcesoDTO> findProcesosByRequirienteId(Integer id);
    //public List<ProcesoLigeroDTO> findProcesosWherePersonaIsOwner(Integer id);
    public Object  insert(ProcesoDTO procesoDTO);
    public Object  insertProcesoPagoDocente(ProcesoPagoDocenteResponsablesDTO procesoDTO);
    public ProcesoDTO update(ProcesoDTO dto);
    public void delete(Integer id);
    public Object  findByIdCompletoDTO(Integer id);
    public List<MiProcesoDTO> findMisProcesosGeneral();
    public List<MiProcesoDTO> findMisProcesosGeneralPorResponsable(Integer responsableId);   
    public List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocente();
    public List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocentePorResponsable(Integer responsableId);   
    public List<ProcesoPasoDocumentoDTO> obtenerDetalleProcesoId(Integer procesoId);
}
