package es.daw.jakarta.jdbcapp.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * T → el tipo de entidad (Producto, Usuario, etc.)
 *
 * ID → el tipo de la clave primaria (Integer, Long, String...)
 *
 * save() → inserta un nuevo registro en la BD.
 *
 * findById() → devuelve un Optional<T> (puede existir o no el registro).
 *
 * findAll() → devuelve todos los registros.
 *
 * update() → actualiza un registro existente.
 *
 * delete() → elimina por ID.
 * @param <T>
 * @param <ID>
 */
public interface GenericDAO<T, ID> {

    // CREATE
    void save(T entity) throws SQLException;

    // READ
    Optional<T> findById(ID id) throws SQLException;

    List<T> findAll() throws SQLException;

    // UPDATE
    void update(T entity) throws SQLException;

    // DELETE
    void delete(ID id) throws SQLException;

    // PENDIENTE!!! DECIDIR SI DECLARO UN MÉTODO ABSTRACTO PARA HACER findByNombre!!!
}