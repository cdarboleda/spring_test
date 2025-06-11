package com.security.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.MiProcesoPagoDocenteDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;

@Repository
public interface IProcesoRepository extends JpaRepository<Proceso, Integer> {

        List<Proceso> findByRequirienteId(Integer id);

        // devuelve todos los procesos en general con esa estructura
        // ya no se usa
        @Query("SELECT new com.security.service.dto.MiProcesoDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, " +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula) " +
                        "FROM Proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
                        "LEFT JOIN paso.responsable resp ")
        List<MiProcesoDTO> findMisProcesosGeneral();

        // devuelve los procesos de pago docente, para la tabla de procesos de pago
        // docente
        // hecho para el admin que necesita todos los procesos pd de todos los usuarios
        @Query("SELECT new com.security.service.dto.MiProcesoPagoDocenteDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, "
                        +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula, mat.nombre, mat.horas, mae.codigo, mae.nombre, " +
                        "ppd.fechaInicioClase, ppd.fechaFinClase, mae.cohorte, mae.estado ) " +
                        "FROM ProcesoPagoDocente ppd " +
                        "LEFT JOIN ppd.proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN ppd.materia mat " +
                        "LEFT JOIN mat.maestria mae " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
                        "LEFT JOIN paso.responsable resp ") // Información del responsable del paso
        List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocente();

        // buscar un proceso por id, para la tabla de mis procesos pago docente
        @Query("SELECT new com.security.service.dto.MiProcesoPagoDocenteDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, " +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula, mat.nombre, mat.horas, mae.codigo, mae.nombre, " +
                        "ppd.fechaInicioClase, ppd.fechaFinClase, mae.cohorte, mae.estado ) " +
                        "FROM ProcesoPagoDocente ppd " +
                        "LEFT JOIN ppd.proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN ppd.materia mat " +
                        "LEFT JOIN mat.maestria mae " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
                        "LEFT JOIN paso.responsable resp " +
                        "WHERE p.id = :procesoId")
        MiProcesoPagoDocenteDTO findMiProcesoPagoDocenteById(@Param("procesoId") Integer procesoId);

        // Todos los procesos pd pero solo donde el usuario logueado participe
        @Query("SELECT new com.security.service.dto.MiProcesoPagoDocenteDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, "
                        +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula, mat.nombre, mat.horas, mae.codigo, mae.nombre, " +
                        "ppd.fechaInicioClase, ppd.fechaFinClase, mae.cohorte, mae.estado ) " +
                        "FROM ProcesoPagoDocente ppd " +
                        "LEFT JOIN ppd.proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN ppd.materia mat " +
                        "LEFT JOIN mat.maestria mae " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " +
                        "LEFT JOIN paso.responsable resp " +
                        "WHERE EXISTS (SELECT 1 FROM p.pasos pas WHERE pas.responsable.id = :responsableId)")
        List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocentePorResponsable(
                        @Param("responsableId") Integer responsableId);

        // devuelve todos los procesos de un usuario con la estructura general
        // ya no se usa
        @Query("SELECT new com.security.service.dto.MiProcesoDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, " +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula) " +
                        "FROM Proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " + // Paso en curso
                        "LEFT JOIN paso.responsable resp " +
                        "WHERE EXISTS (SELECT 1 FROM p.pasos pas WHERE pas.responsable.id = :responsableId)")
        List<MiProcesoDTO> findMisProcesosGeneralPorResponsable(@Param("responsableId") Integer responsableId);

        @Query("SELECT new com.security.service.dto.ProcesoPasoDocumentoDTO(" +
                        "pa.id, pa.nombre, pa.estado, pa.descripcionEstado, pa.fechaInicio, pa.fechaFin, pa.orden, pa.observacion, CAST(pa.rol AS string), "
                        +
                        "per.id, per.nombre, per.cedula, per.apellido, per.telefono, per.correo, " +
                        "cd.id, cd.url) " +
                        "FROM Paso pa " +
                        "LEFT JOIN pa.responsable per " +
                        "LEFT JOIN pa.carpetaDocumentos cd " +
                        "WHERE pa.proceso.id = :procesoId " +
                        "ORDER BY pa.orden")
        List<ProcesoPasoDocumentoDTO> findProcesoDetalleById(@Param("procesoId") Integer procesoId);

        @Query("SELECT new com.security.service.dto.ProcesoPasoDocumentoDTO(" +
                        "pa.id, pa.nombre, pa.estado, pa.descripcionEstado, pa.fechaInicio, pa.fechaFin, pa.orden, pa.observacion, CAST(pa.rol AS string), "
                        +
                        "per.id, per.nombre, per.cedula, per.apellido, per.telefono, per.correo, " +
                        "cd.id, cd.url) " +
                        "FROM Paso pa " +
                        "LEFT JOIN pa.responsable per " +
                        "LEFT JOIN pa.carpetaDocumentos cd " +
                        "WHERE pa.id = :pasoId")
        ProcesoPasoDocumentoDTO findPasoDetalleById(@Param("pasoId") Integer pasoId);

        @Query("SELECT DISTINCT pa.responsable.idKeycloak " +
                        "FROM Paso pa " +
                        "WHERE pa.proceso.id = :procesoId AND pa.responsable IS NOT NULL")
        List<String> responsablesDeUnProceso(@Param("procesoId") Integer procesoId);

}
