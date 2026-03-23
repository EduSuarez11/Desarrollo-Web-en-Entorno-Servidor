-- ===============================================
-- SCRIPT: schema.sql
-- Base de datos H2 en memoria (Cliente - Pedido)
-- ===============================================

DROP TABLE IF EXISTS Pedido;
DROP TABLE IF EXISTS Cliente;

-- ===============================================
-- TABLA CLIENTE
-- ===============================================
CREATE TABLE Cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(150) NOT NULL,
                         nif VARCHAR(20) NOT NULL UNIQUE,
                         email VARCHAR(255),
                         fecha_registro DATE DEFAULT CURRENT_DATE
);

-- ===============================================
-- TABLA PEDIDO
-- ===============================================
CREATE TABLE Pedido (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        cliente_id BIGINT NOT NULL,
                        fecha_pedido TIMESTAMP NOT NULL,
                        total DECIMAL(10,2) NOT NULL,
                        estado VARCHAR(20) CHECK (estado IN ('PENDIENTE', 'ENTREGADO', 'CANCELADO')),
                        FOREIGN KEY (cliente_id) REFERENCES Cliente(id) ON DELETE RESTRICT
);

-- ===============================================
-- CLIENTES
-- ===============================================
INSERT INTO Cliente (nombre, nif, email, fecha_registro)
VALUES
    ('Juan Pérez', '12345678A', 'juan.perez@example.com', DATE '2024-02-15'),
    ('María Gómez', '87654321B', 'maria.gomez@example.com', DATE '2023-11-10'),
    ('Carlos Ruiz', '11223344C', 'carlos.ruiz@example.com', DATE '2022-06-25'),
    ('Mari Luz Elola', '66666666A', 'melola@educa.madrid.org', DATE '2025-10-22');

-- ===============================================
-- PEDIDOS CLIENTE 1 (8 pedidos)
-- ===============================================
INSERT INTO Pedido (cliente_id, fecha_pedido, total, estado) VALUES
                                                                 (1, TIMESTAMP '2024-03-01 10:30:00', 250.75, 'ENTREGADO'),
                                                                 (1, TIMESTAMP '2024-03-05 14:45:00', 89.99, 'PENDIENTE'),
                                                                 (1, TIMESTAMP '2024-03-10 09:20:00', 310.50, 'CANCELADO'),
                                                                 (1, TIMESTAMP '2024-04-01 16:10:00', 120.00, 'ENTREGADO'),
                                                                 (1, TIMESTAMP '2024-04-15 11:25:00', 450.25, 'ENTREGADO'),
                                                                 (1, TIMESTAMP '2024-05-12 18:45:00', 99.99, 'PENDIENTE'),
                                                                 (1, TIMESTAMP '2024-05-20 20:00:00', 75.25, 'PENDIENTE'),
                                                                 (1, TIMESTAMP '2024-06-02 08:15:00', 640.80, 'CANCELADO');

-- ===============================================
-- PEDIDOS CLIENTE 2 (10 pedidos)
-- ===============================================
INSERT INTO Pedido (cliente_id, fecha_pedido, total, estado) VALUES
                                                                 (2, TIMESTAMP '2024-04-08 09:15:00', 430.50, 'CANCELADO'),
                                                                 (2, TIMESTAMP '2024-04-12 12:30:00', 220.00, 'PENDIENTE'),
                                                                 (2, TIMESTAMP '2024-04-15 10:10:00', 315.75, 'ENTREGADO'),
                                                                 (2, TIMESTAMP '2024-05-01 14:50:00', 150.40, 'PENDIENTE'),
                                                                 (2, TIMESTAMP '2024-05-10 17:25:00', 640.00, 'ENTREGADO'),
                                                                 (2, TIMESTAMP '2024-05-15 20:05:00', 78.90, 'PENDIENTE'),
                                                                 (2, TIMESTAMP '2024-06-02 09:40:00', 199.99, 'ENTREGADO'),
                                                                 (2, TIMESTAMP '2024-06-14 16:45:00', 89.95, 'CANCELADO'),
                                                                 (2, TIMESTAMP '2024-07-01 11:00:00', 55.25, 'PENDIENTE'),
                                                                 (2, TIMESTAMP '2024-07-10 19:20:00', 480.10, 'ENTREGADO');

-- ===============================================
-- PEDIDOS CLIENTE 3 (6 pedidos)
-- ===============================================
INSERT INTO Pedido (cliente_id, fecha_pedido, total, estado) VALUES
                                                                 (3, TIMESTAMP '2024-06-14 14:20:00', 129.00, 'ENTREGADO'),
                                                                 (3, TIMESTAMP '2024-06-20 15:15:00', 315.50, 'PENDIENTE'),
                                                                 (3, TIMESTAMP '2024-07-05 09:00:00', 89.90, 'CANCELADO'),
                                                                 (3, TIMESTAMP '2024-07-15 12:30:00', 75.25, 'ENTREGADO'),
                                                                 (3, TIMESTAMP '2024-08-01 20:10:00', 210.00, 'PENDIENTE'),
                                                                 (3, TIMESTAMP '2024-08-12 10:05:00', 130.00, 'ENTREGADO');
