package es.daw.jakarta.pedidosexamen.repository;

import es.daw.jakarta.pedidosexamen.model.Cliente;
import es.daw.jakarta.pedidosexamen.model.Pedido;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO implements GenericDAO<Cliente, Long>{
    private Connection conn;

    public ClienteDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }
    @Override
    public void save(Cliente entity) throws SQLException {

    }

    @Override
    public Optional<Cliente> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Cliente> findAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            // BigInteger id_cliente, LocalDateTime fechaPedido, double total, EstadoPedido estado)
            while(rs.next()){
                clientes.add(
                        new Cliente(
                                rs.getLong("id"),
                                rs.getString("nombre"),
                                rs.getString("nif"),
                                rs.getString("email"),
                                rs.getDate("fecha_registro").toLocalDate()
                        )
                );
            }
        }
        return clientes;
    }

    @Override
    public void update(Cliente entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Cliente> findByIdCliente(Long aLong) throws SQLException {
        return List.of();
    }
}
