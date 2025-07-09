-- Insertar personas
INSERT INTO app_schema.persona (
    pers_id, pers_activo, pers_apellido, pers_cedula, pers_correo,
    pers_id_keycloak, pers_nombre, pers_telefono
) VALUES
(1, true, 'sigepro', 'xxxxxxxxxx', 'fing.consejo.posgrado@gmail.com', null, 'administrador', null);

-- Insertar roles
INSERT INTO app_schema.rol (
    rol_id, rol_descripcion, rol_nombre
) VALUES
(1, 'Administrador', 'administrador'),
(2, 'Secretaria', 'secretaria'),
(3, 'Director', 'director'),
(4, 'Estudiante', 'estudiante'),
(5, 'Docente', 'docente');

-- Insertar persona_rol
INSERT INTO app_schema.persona_rol (pers_id, rol_id) VALUES
(1, 1);
