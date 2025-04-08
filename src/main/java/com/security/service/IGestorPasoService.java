package com.security.service;

import java.util.List;

import com.security.db.Paso;
import com.security.service.dto.PasoDTO;

public interface IGestorPasoService {
    // public List<Paso> findPasosByResponsableId(Integer idResponsable);
    public Paso insertarUnico(PasoDTO pasoDTO);
    public List<PasoDTO> crearPasos(String proceso);
    public PasoDTO updatePasoResponsable(Integer idPaso, Integer idResponsable);
    public List<PasoDTO> findPasosDTOByProcesoId(Integer procesoId);
    public Paso updatePaso(Integer idPaso, PasoDTO pasoDTO);
    
}
