package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Integer>{

}
