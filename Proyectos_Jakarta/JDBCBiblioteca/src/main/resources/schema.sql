-- Crear la tabla de autores
CREATE TABLE IF NOT EXISTS Autor (
                        id BIGINT PRIMARY KEY,
                        nombre VARCHAR(255) NOT NULL
);

-- Crear la tabla de libros con un campo de fecha de publicación
CREATE TABLE IF NOT EXISTS Libro (
                      id BIGINT PRIMARY KEY,
                      titulo VARCHAR(255) NOT NULL,
                      autor_id BIGINT,
                      publicacion_fecha DATE,  -- Nuevo campo para la fecha de publicación
                      FOREIGN KEY (autor_id) REFERENCES Autor(id) ON DELETE CASCADE
);




-- Insertar algunos datos de ejemplo en la tabla de autores
INSERT INTO Autor (id, nombre) VALUES (1,'Gabriel García Márquez');
INSERT INTO Autor (id, nombre) VALUES (2,'Julio Cortázar');
INSERT INTO Autor (id, nombre) VALUES (3,'Jorge Luis Borges');
INSERT INTO Autor (id, nombre) VALUES (4,'Juan Rulfo');
INSERT INTO Autor (id, nombre) VALUES (5,'Ernesto Sabato');
INSERT INTO Autor (id, nombre) VALUES (6,'Isabel Allende');
INSERT INTO Autor (id, nombre) VALUES (7,'Mario Vargas Llosa');
INSERT INTO Autor (id, nombre) VALUES (8,'Roberto Bolaño');


-- Insertar algunos libros asociados a los autores con fechas de publicación
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (1,'Cien años de soledad', 1, DATE '1967-06-05');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (2,'La vuelta al día en ochenta mundos', 2, DATE '1970-10-01');

-- Libros de Jorge Luis Borges (id = 3)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (3,'Ficciones', 3, DATE '1944-01-01');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (4,'Conversación en La Catedral', 7, DATE '1969-05-01');


-- Libros de Juan Rulfo (id = 4)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (5,'La casa de los espíritus', 6, DATE '1982-10-15');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (6,'Los detectives salvajes', 8, DATE '1998-04-01');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (7,'Pedro Páramo', 4, DATE '1955-03-19');

-- Libros de Ernesto Sabato (id = 5)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (8,'El túnel', 5, DATE '1948-09-05');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (9,'El Aleph', 3, DATE '1949-01-01');

-- Libros de Isabel Allende (id = 6)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (10,'De amor y de sombra', 6, DATE '1984-05-01');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (11,'Rayuela', 2, DATE '1963-06-28');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (12,'Sobre héroes y tumbas', 5, DATE '1961-07-01');

-- Libros de Mario Vargas Llosa (id = 7)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (13,'La ciudad y los perros', 7, DATE '1963-10-01');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (14,'El amor en los tiempos del cólera', 1, DATE '1985-04-05');

-- Libros de Roberto Bolaño (id = 8)
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (15,'2666', 8, DATE '2004-11-01');
INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (16,'El llano en llamas', 4, DATE '1953-09-01');


