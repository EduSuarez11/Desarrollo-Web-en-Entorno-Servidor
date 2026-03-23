package es.daw.jakarta.jdbcapp.repository;

import es.daw.jakarta.jdbcapp.model.Fabricante;
import es.daw.jakarta.jdbcapp.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FabricanteDAO implements GenericDAO<Fabricante,Integer>{
    private Connection conn;

    public FabricanteDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    @Override
    public void save(Fabricante entity) throws SQLException {
    }

    @Override
    public Optional<Fabricante> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM Fabricante WHERE codigo = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Fabricante fabricante = new Fabricante(
                        rs.getInt("codigo"),
                        rs.getString("nombre")
                );
                return Optional.of(fabricante);
            }
        }

        return Optional.empty();


    }

    @Override
    public List<Fabricante> findAll() throws SQLException {

        List<Fabricante> fabricantes = new ArrayList<>();
        String sql = "SELECT * FROM fabricante";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                fabricantes.add(
                        new Fabricante(
                                rs.getInt("codigo"),
                                rs.getString("nombre")
                        )
                );
            }
        }
        System.out.println("******* Fabricantes *******");
        fabricantes.forEach(System.out::println);
        return fabricantes;

    }

    @Override
    public void update(Fabricante entity) throws SQLException {
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM fabricante WHERE codigo = ?")){
            ps.setInt(1, codigo);
            ps.executeUpdate();
        }

    }
    // -----------------------------
    // PENDIENTE!!! Puedo crear los métodos que me de la gana como findByName...
    // Creo findByName en el caso de que no lo declare en la interface
    // En cualquier caso debo implementarlo aquí!!!!


}
