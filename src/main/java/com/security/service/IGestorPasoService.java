package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Paso;
import com.security.service.dto.PasoDTO;

public interface IGestorPasoService {
    // public List<Paso> findPasosByResponsableId(Integer idResponsable);
    public Paso insertarUnico(PasoDTO pasoDTO);
    public List<Paso> insertarMultipleConResponsable(List<PasoDTO> pasosDTO, Integer idProceso);
    public List<Paso> insertarMultipleAProceso(List<PasoDTO> pasosDTO, Integer idProceso);
    public List<PasoDTO> crearPasos(String proceso);
    public Paso updatePasoResponsable(Integer idPaso, Integer idResponsable);
}
