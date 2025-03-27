package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Proceso;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.MiProcesoPagoDocenteDTO;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.ProcesoPasoDocumentoDTO;

@Repository
public interface IProcesoRepository extends JpaRepository<Proceso, Integer> {

        List<Proceso> findByRequirienteId(Integer id);

        // Aqui estaba la que me devolvia todos los procesos cuando soy admin,
        // pero como mostraba la materia si no es de pago docente?,
        // quiza sirva moviendole para que muestre info general de cualquier tipo de
        // proceso
        // pues como estaba antes

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

        @Query("SELECT new com.security.service.dto.MiProcesoPagoDocenteDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, "
                        +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula, mat.codigo, mae.maestria.codigo ) " +
                        "FROM ProcesoPagoDocente ppd " +
                        "LEFT JOIN ppd.proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN ppd.materia mat " +
                        "LEFT JOIN mat.maestriaDetalle mae " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " + // Filtra paso en curso
                        "LEFT JOIN paso.responsable resp ") // Información del responsable del paso
        List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocente();

        // Tabla Mis Procesos para un usuario normal
        @Query("SELECT new com.security.service.dto.MiProcesoPagoDocenteDTO( " +
                        "p.id, p.tipoProceso, p.fechaInicio, p.fechaFin, p.finalizado, p.cancelado, "
                        +
                        "req.id, req.cedula, req.nombre, req.apellido, " +
                        "paso.nombre, CAST(paso.estado AS string), paso.descripcionEstado, paso.fechaInicio, " +
                        "resp.id, resp.cedula, mat.codigo, mae.maestria.codigo ) " +
                        "FROM ProcesoPagoDocente ppd " +
                        "LEFT JOIN ppd.proceso p " +
                        "LEFT JOIN p.requiriente req " +
                        "LEFT JOIN ppd.materia mat " +
                        "LEFT JOIN mat.maestriaDetalle mae " +
                        "LEFT JOIN p.pasos paso ON paso.estado = 'EN_CURSO' " + // Filtra paso en curso
                        "LEFT JOIN paso.responsable resp " +
                        "WHERE EXISTS (SELECT 1 FROM p.pasos pas WHERE pas.responsable.id = :responsableId)")
        List<MiProcesoPagoDocenteDTO> findMisProcesosPagoDocentePorResponsable(
                        @Param("responsableId") Integer responsableId);

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
                        "p.id, p.descripcion, " +
                        "pa.id, pa.nombre, pa.estado, pa.descripcionEstado, pa.fechaInicio, pa.fechaFin, pa.orden, pa.observacion, pa.rol.nombre, "
                        +
                        "per.id, per.nombre, per.cedula, per.apellido, per.telefono, per.correo, " +
                        "cd.id, cd.url) " +
                        "FROM Proceso p " +
                        "LEFT JOIN p.pasos pa " +
                        "LEFT JOIN pa.responsable per " +
                        "LEFT JOIN pa.carpetaDocumentos cd " +

                        "WHERE p.id = :procesoId " +
                        "ORDER BY pa.orden")
        List<ProcesoPasoDocumentoDTO> findProcesoDetalleById(@Param("procesoId") Integer procesoId);

}
