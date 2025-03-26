package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.MaestriaDetalle;

public interface IMaestriaDetalleRepository extends JpaRepository<MaestriaDetalle, Integer> {
}
