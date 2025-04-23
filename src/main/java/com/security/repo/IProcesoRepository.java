package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Proceso;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;

@Repository
public interface IProcesoRepository extends JpaRepository<Proceso, Integer> {

        // @Query("SELECT p FROM Proceso p WHERE p.persona.id = :id")
        // List<Proceso> findProcesosWherePersonaIsOwner(@Param("id") Integer id);
        List<Proceso> findByRequirienteId(Integer id);

        // @Query("SELECT new com.security.service.dto.MiProcesoDTO( " +
        // "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, " +
        // "req.id, req.cedula, " +
        // "paso.nombre, paso.estado.toString(), paso.descripcionEstado, " +
        // "resp.id, resp.cedula) " +
        // "FROM Proceso p " +
        // "LEFT JOIN p.requiriente req " +
        // "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
        // "LEFT JOIN paso.responsable resp")
        // List<MiProcesoDTO> findMisProcesos();

        @Query("SELECT new com.security.service.dto.MiProcesoDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, " +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, " +
                        "resp.id, resp.cedula) " +
                        "FROM Proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
                        "LEFT JOIN paso.responsable resp ")
        List<MiProcesoDTO> findMisProcesos();

        // Trae todos los procesos donde haya almenos un paso en el que yo sea
        // responsable (messirve con el otro metodo en el service)
        @Query("SELECT DISTINCT p " +
                        "FROM Proceso p JOIN p.pasos paso " +
                        "WHERE paso.responsable.id = :responsableId")
        List<Proceso> findProcesosByResponsableId(@Param("responsableId") Integer responsableId);

        // Trae todos los procesos donde yo sea responsable de almenos uno, y tambien
        // busca el paso que sea EN_curso (messirve solito, pero quiza es demasiado sql)
        @Query("SELECT new com.security.service.dto.MiProcesoDTO" +
                        "(p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, req.id, req.cedula, "
                        +
                        " pasoEnCurso.nombre, pasoEnCurso.responsable.id, pasoEnCurso.responsable.cedula) " +
                        "FROM Proceso p " +
                        "JOIN p.requiriente req " +
                        "LEFT JOIN p.pasos pasoEnCurso ON pasoEnCurso.estado = 'EN_CURSO' " +
                        "WHERE EXISTS (SELECT 1 FROM Paso paso WHERE paso.proceso.id = p.id AND paso.responsable.id = :responsableId)")
        List<MiProcesoDTO> findMisProcesosByResponsableId(@Param("responsableId") Integer responsableId);

        // Trea un proceso especifico con sus pasos y los documentos en cada uno de
        // ellos
        // @Query("SELECT new com.security.service.dto.ProcesoPasoDocumentoDTO(" +
        // "p.id, p.descripcion, " +
        // "pa.id, pa.nombre, pa.descripcionPaso, pa.estado, pa.fechaInicio,
        // pa.fechaFin, pa.orden, " +
        // "per.id, per.nombre, per.cedula, per.apellido,"+
        // "cd.id, cd.url) " +
        // "FROM Proceso p " +
        // "LEFT JOIN p.pasos pa " +
        // "LEFT JOIN pa.responsable per " +
        // "LEFT JOIN p.carpetasDocumento cd " +
        // "WHERE p.id = :procesoId " +
        // "ORDER BY p.id, pa.orden")
        // List<ProcesoPasoDocumentoDTO> findProcesoDetalleById(@Param("procesoId")
        // Integer procesoId);

        @Query("SELECT new com.security.service.dto.ProcesoPasoDocumentoDTO(" +
                        "p.id, p.descripcion, " +
                        "pa.id, pa.nombre, pa.descripcionPaso, pa.estado, pa.descripcionEstado, pa.fechaInicio, pa.fechaFin, pa.orden, pa.observacion, pa.rol.nombre, "
                        +
                        "per.id, per.nombre, per.cedula, per.apellido, per.telefono, per.correo, " +
                        "cd.id, cd.url) " +
                        "FROM Proceso p " +
                        "LEFT JOIN p.pasos pa " +
                        "LEFT JOIN pa.responsable per " +
                        "LEFT JOIN pa.carpetaDocumentos cd " + // Cambiado de `p.carpetasDocumento` a
                                                               // `pa.carpetasDocumento`
                        "WHERE p.id = :procesoId " +
                        "ORDER BY pa.orden")
        List<ProcesoPasoDocumentoDTO> findProcesoDetalleById(@Param("procesoId") Integer procesoId);
}
