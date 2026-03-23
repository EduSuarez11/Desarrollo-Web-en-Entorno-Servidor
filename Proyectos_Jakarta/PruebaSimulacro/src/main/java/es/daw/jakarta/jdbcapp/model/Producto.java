package es.daw.jakarta.jdbcapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Un JavaBean debe tener un constructor sin argumentos.
 * Tiene declarados todos sus atributos como privados y para cada uno de ellos un método setter y
 * getter. Deben ser serializables. Mediante estos JavaBeans, desarrollamos
 * nuestro modelo de objetos (o modelo de dominio) para la aplicación.
 *
 *
 * CURIOSIDAD: POJO son las iniciales de "Plain Old Java Object", que puede
 * interpretarse como "Un objeto Java Plano Antiguo".
 * Clase con atributos y constructores... POJO.
 *
 */

// PENDIENTE!!! REVISAR INTERFACE SERIALIZABLE .....
public class Producto implements Serializable {
    private Integer codigo;
    private String nombre;
    // los decimales para precio: no es recomendable ni float ni double porque son binarios en coma flotante
    // coma flotante pierde presión
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
