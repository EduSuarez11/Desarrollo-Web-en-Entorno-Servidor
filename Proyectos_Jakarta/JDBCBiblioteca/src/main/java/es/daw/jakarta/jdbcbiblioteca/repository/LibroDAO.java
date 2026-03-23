package es.daw.jakarta.jdbcbiblioteca.repository;

import es.daw.jakarta.jdbcbiblioteca.model.Libro;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO implements GenericDAO<Libro,Integer>{

    Connection conn;

    public LibroDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    @Override
    public void save(Libro entity) throws SQLException {
        String sql = "INSERT INTO Libro (id, titulo, autor_id, publicacion_fecha) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getTitulo());
            ps.setInt (3, entity.getAutor_id());
            ps.setDate(4, (Date) entity.getFecha_publicacion());
            ps.executeUpdate();
        }

    }

    @Override
    public Optional<Libro> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Libro WHERE autor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return Optional.of(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("autor_id"),
                        rs.getDate("publicacion_fecha")
                        )
                );
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Libro> findAll() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libro";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("autor_id"),
                        rs.getDate("publicacion_fecha")
                        )
                );
            }

        }
        return libros;
    }

    @Override
    public void update(Libro entity) throws SQLException {
        String sql = "UPDATE Libro SET titulo = ?, autor_id = ?, publicacion_fecha = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTitulo());
            ps.setInt(2, entity.getAutor_id());
            ps.setDate(3, (Date) entity.getFecha_publicacion());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        }

    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Libro WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    @Override
    public List<Libro> findByAutor(Integer id_autor) throws SQLException {
        String sql = "SELECT * FROM Libro WHERE autor_id = ?";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_autor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("autor_id"),
                        rs.getDate("publicacion_fecha")
                        )
                );
            }
        }
        return libros;
    }

    @Override
    public List<Libro> findByFecha(Date fechaInicio, Date fechaFin) throws SQLException {
        String sql = "SELECT * FROM Libro WHERE publicacion_fecha >= ? AND publicacion_fecha <= ?";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("autor_id"),
                        rs.getDate("publicacion_fecha")
                        )
                );
            }
        }
        return libros;
    }


}
