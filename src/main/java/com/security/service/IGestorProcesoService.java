package com.security.service;

import java.util.List;

import com.security.db.Paso;
import com.security.db.Proceso;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;
import com.security.service.dto.ProcesoDTO;

public interface IGestorProcesoService {
    public List<ProcesoDTO> findProcesosByRequirienteId(Integer id);
    //public List<ProcesoLigeroDTO> findProcesosWherePersonaIsOwner(Integer id);
    public Object  insert(ProcesoDTO procesoDTO);
    public ProcesoDTO update(ProcesoDTO dto);
    public void delete(Integer id);
    public Object  findByIdCompletoDTO(Integer id);
    public List<MiProcesoDTO> findMisProcesos();
    public List<MiProcesoDTO> obtenerMisProcesos(Integer responsableId);
    public List<MiProcesoDTO> findMisProcesosByResponsableId(Integer responsableId);
    
    public List<ProcesoPasoDocumentoDTO> obtenerDetalleProcesoId(Integer procesoId);
}
