package es.daw.jakarta.jdbcappfase2.model;

import java.math.BigDecimal;

public class Producto {
    private Integer codigo;
    private String nombre;

    // No es recomendable usar float ni double para representar dinero,
    // porque son binarios de coma flotante y producen errores de precisión.
    /*
        Beneficios de BigDecimal:
            Maneja decimales exactos.
            Ideal para dinero o cantidades con precisión.
            JDBC mapea automáticamente DECIMAL ↔ BigDecimal.
     */
    private BigDecimal precio;
    private Integer codigo_fabricante;

    public Producto(Integer codigo, String nombre, BigDecimal precio, Integer codigo_fabricante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo_fabricante = codigo_fabricante;
    }

    public Producto() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCodigo_fabricante() {
        return codigo_fabricante;
    }

    public void setCodigo_fabricante(Integer codigo_fabricante) {
        this.codigo_fabricante = codigo_fabricante;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", codigo_fabricante=" + codigo_fabricante +
                '}';
    }
}
