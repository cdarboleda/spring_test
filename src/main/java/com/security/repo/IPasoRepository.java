package com.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Paso;
import com.security.db.Proceso;

public interface IPasoRepository extends JpaRepository<Paso, Integer>{
    public Optional<Paso> findByOrden(Integer orden);
    List<Paso> findByResponsableId(Integer idResponsable);
    List<Proceso> findByProcesoId(Integer idProceso);

}
