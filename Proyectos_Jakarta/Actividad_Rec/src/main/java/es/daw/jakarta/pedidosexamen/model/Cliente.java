package es.daw.jakarta.pedidosexamen.model;

import java.time.LocalDate;

/**
 * Representa un cliente en el sistema de gestión de pedidos.
 */
public class Cliente {
    private Long id;
    private String nombre;
    private String nif;
    private String email;
    private LocalDate fechaRegistro;

    // ===== CONSTRUCTORES =====
    public Cliente() {
    }

    public Cliente(Long id,String nombre, String nif, String email, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }



    // ===== GETTERS & SETTERS =====


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // ===== MÉTODOS AUXILIARES =====

    @Override
    public String toString() {
        return "Cliente{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", nif='" + nif + '\'' +
               ", email='" + email + '\'' +
               ", fechaRegistro=" + fechaRegistro +
               '}';
    }
}
