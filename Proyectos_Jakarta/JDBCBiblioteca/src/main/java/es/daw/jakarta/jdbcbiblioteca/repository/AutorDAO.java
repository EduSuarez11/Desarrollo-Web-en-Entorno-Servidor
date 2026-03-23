package es.daw.jakarta.jdbcbiblioteca.repository;

import es.daw.jakarta.jdbcbiblioteca.model.Autor;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorDAO implements GenericDAO<Autor,Integer>{

    Connection conn;


    public AutorDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    @Override
    public void save(Autor entity) throws SQLException {
        String sql = "INSERT INTO Autor (id, nombre) VALUES (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        }

    }

    @Override
    public Optional<Autor> findById(Integer integer) throws SQLException {
        String sql = "SELECT * FROM Autor WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, integer);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return Optional.of(new Autor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                        )
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Autor> findAll() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autor";
        conn = DBConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                autores.add(new Autor(
                                rs.getInt("id"),
                                rs.getString("nombre")
                        )
                );
            }
        }
        return autores;
    }

    @Override
    public void update(Autor entity) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Autor WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Autor> findByAutor(Integer id_autor) throws SQLException {
        return List.of();
    }

    @Override
    public List<Autor> findByFecha(Date fechaInicio, Date fechaFin) throws SQLException {
        return List.of();
    }
}
