package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.ProcesoPaso;

public interface IProcesoPasoRepository extends JpaRepository<ProcesoPaso, Integer>{
    

}
