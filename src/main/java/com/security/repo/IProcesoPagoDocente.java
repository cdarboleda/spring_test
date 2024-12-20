package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.db.ProcesoPagoDocente;

@Repository
public interface IProcesoPagoDocente extends JpaRepository<ProcesoPagoDocente, Integer>{
    
}
