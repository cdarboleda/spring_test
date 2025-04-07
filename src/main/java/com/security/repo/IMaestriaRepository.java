package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.Maestria;

public interface IMaestriaRepository extends JpaRepository<Maestria, Integer> {

    @Query("SELECT m "+
    "FROM Maestria m WHERE m.codigo = :codigo "+
    "AND m.nombre = :nombre " +
    "AND m.cohorte = :cohorte ")
    Maestria findMaestriaPorCodigoNombreCohorte(@Param("codigo") String codigo,
     @Param("nombre") String nombre, @Param("cohorte") String cohorte);
}
