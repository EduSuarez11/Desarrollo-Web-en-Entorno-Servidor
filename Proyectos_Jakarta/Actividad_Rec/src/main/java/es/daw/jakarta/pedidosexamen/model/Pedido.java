package es.daw.jakarta.pedidosexamen.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Representa un pedido realizado por un cliente.
 */
public class Pedido implements Comparable<Pedido> {

    private Long id;
    private Long id_cliente;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private EstadoPedido estado;




    // ===== ENUMERADO =====
    public enum EstadoPedido {
        PENDIENTE,
        ENTREGADO,
        CANCELADO
    }

    // ===== CONSTRUCTORES =====
    public Pedido() {
    }

    public Pedido(Long id_cliente, LocalDateTime fechaPedido, BigDecimal total, EstadoPedido estado) {
        this.id_cliente = id_cliente;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.estado = estado;
    }

    @Override
    public int compareTo(Pedido p1) {
        return p1.getEstado().compareTo(this.estado);
    }


    // ===== GETTERS & SETTERS =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    // ===== MÉTODOS AUXILIARES =====


    @Override
    public String toString() {
        return "Pedido{" +
               "id=" + id +
               ", id_cliente=" + id_cliente +
               ", fechaPedido=" + fechaPedido +
               ", total=" + total +
               ", estado=" + estado +
               '}';
    }
}
