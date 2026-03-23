package es.daw.jakarta.jdbcapp.repository;

import es.daw.jakarta.jdbcapp.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAO implements GenericDAO<Producto,Integer>{
    private Connection conn;

    public ProductoDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    @Override
    public void save(Producto entity) throws SQLException {
        // Si queremos controlar que el código del nuevo producto (entity) no esté duplicado antes de insertar...
        // Forma 1: consultar sql si existe el código findById. Si no devuelve nada, no existe, si devuelve un reg, exite...
        // Llamaría a findById desde el servlet
        // Forma 2: sql con insert if not exist... qué devuelve la BD si ya existe el código???
        // Forma 3: recogiendo la excepción de la BD  (LA QUE VAMOS A HACER AHORA)
        // Forma 4: hacemos findAll y busco en la lista si existe un producto con ese código... contains!!!
        // Obligatoriamente debo implementar equals/hashcode en Producto y creo un Producto nuevo solo con dicho código...

        String sql = "insert into producto (codigo,nombre, precio,codigo_fabricante) values (?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.setBigDecimal(3, entity.getPrecio());
            ps.setInt(4, entity.getCodigo_fabricante());

            ps.executeUpdate();
        }

    }

    @Override
    public Optional<Producto> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM producto WHERE codigo = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Producto producto = new Producto(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("codigo_fabricante")
                );
                return Optional.of(producto);
            }
        }

        return Optional.empty();


    }

    @Override
    public List<Producto> findAll() throws SQLException {
        //return List.of();

        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                productos.add(
                        new Producto(
                                rs.getInt("codigo"),
                                rs.getString("nombre"),
                                rs.getBigDecimal("precio"),
                                rs.getInt("codigo_fabricante")
                        )
                );
            }
        }
        return productos;

    }

    @Override
    public void update(Producto entity) throws SQLException {
        String sql = "UPDATE producto SET  nombre=?, precio=?, codigo_fabricante=? WHERE codigo=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, entity.getNombre());
            ps.setBigDecimal(2, entity.getPrecio());
            ps.setInt(3, entity.getCodigo_fabricante());
            ps.setInt(4, entity.getCodigo());

            ps.executeUpdate();
        }

    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM producto WHERE codigo = ?")){
            ps.setInt(1, codigo);
            ps.executeUpdate();
        }

    }
    // -----------------------------
    // PENDIENTE!!! Puedo crear los métodos que me de la gana como findByName...
    // Creo findByName en el caso de que no lo declare en la interface
    // En cualquier caso debo implementarlo aquí!!!!


}
