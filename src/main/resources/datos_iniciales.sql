
-- Insertar personas
INSERT INTO app_schema.persona (
    pers_id,pers_activo, pers_apellido, pers_cedula, pers_correo,
    pers_id_keycloak, pers_nombre, pers_telefono
) VALUES


-- Secretaria
(1, true, 'Torres Avila',     '0808080808', 'torres.secretaria@ejemplo.com',  null , 'Lucía Diana',    '0999990008'),

-- Director
(2, true, 'Navarro Ferreira', '0909090909', 'navarro.director@ejemplo.com',  null , 'José Mario',     '0999990009');

-- Insertar persona_rol (asumiendo que IDs se asignan automáticamente en orden)
INSERT INTO app_schema.persona_rol (pers_id, rol_id) VALUES

(1, 3),
(2, 4);
