-- Tipos de evaluación (catálogo)
INSERT INTO tipo_evaluacion (nombre) VALUES ('Primera Evaluación');
INSERT INTO tipo_evaluacion (nombre) VALUES ('Segunda Evaluación');
INSERT INTO tipo_evaluacion (nombre) VALUES ('Tercera Evaluación');
INSERT INTO tipo_evaluacion (nombre) VALUES ('Ordinaria');
INSERT INTO tipo_evaluacion (nombre) VALUES ('Extraordinaria');

-- Cursos
INSERT INTO curso (nombre, descripcion) VALUES ('1DAW', '1º curso de Desarrollo de Aplicaciones Web');
INSERT INTO curso (nombre, descripcion) VALUES ('2DAW', '2º curso de Desarrollo de Aplicaciones Web');

-- Evaluaciones (curso x tipo, sin código textual)
-- 1DAW: 5 tipos
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (1, 1);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (1, 2);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (1, 3);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (1, 4);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (1, 5);
-- 2DAW: sin Tercera Evaluación
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (2, 1);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (2, 2);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (2, 4);
INSERT INTO evaluacion (curso_id, tipo_evaluacion_id) VALUES (2, 5);

-- Alumnos
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12345', 'Ana', 'García López');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12346', 'Luis', 'Martínez Ruiz');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12347', 'Marta', 'Sánchez Díaz');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12348', 'Pedro', 'Jiménez Mora');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12400', 'Sofía', 'Fernández Gil');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12401', 'Carlos', 'Romero Vega');
INSERT INTO alumno (nia, nombre, apellidos) VALUES ('12402', 'Laura', 'Torres Blanco');

-- Notas (nia → alumno, evaluacion_id según orden del INSERT anterior)
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12345', 1, 8);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12346', 1, 7);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12347', 1, 6);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12348', 2, 9);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12345', 2, 5);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12346', 3, 4);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12348', 4, 8);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12345', 5, 7);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12346', 5, 6);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12347', 5, 5);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12400', 6, 7);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12401', 6, 9);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12402', 6, 6);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12400', 7, 8);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12401', 7, 7);
INSERT INTO nota (nia, evaluacion_id, calificacion) VALUES ('12402', 7, 5);