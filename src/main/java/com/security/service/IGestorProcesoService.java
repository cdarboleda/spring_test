package com.security.service;

import java.util.List;

import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoLigeroDTO;

public interface IGestorProcesoService {
    public List<ProcesoLigeroDTO> findProcesosByPersonaId(Integer id);
    //public List<ProcesoLigeroDTO> findProcesosWherePersonaIsOwner(Integer id);
    public ProcesoLigeroDTO insert(ProcesoDTO dto);
    public ProcesoLigeroDTO update(ProcesoDTO dto);
    public void delete(Integer id);
    public ProcesoCompletoDTO findByIdCompletoDTO(Integer id);
}
