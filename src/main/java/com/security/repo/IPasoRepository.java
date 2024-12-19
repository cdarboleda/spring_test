package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Paso;

public interface IPasoRepository extends JpaRepository<Paso, Integer>{
    

}
