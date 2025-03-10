package com.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Materia;
import com.security.db.Rol;

public interface IMateriaRepository extends JpaRepository<Materia, Integer> {

}
