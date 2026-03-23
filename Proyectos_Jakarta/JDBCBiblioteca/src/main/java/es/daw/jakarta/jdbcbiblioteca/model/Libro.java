package es.daw.jakarta.jdbcbiblioteca.model;

import java.util.Comparator;
import java.util.Date;

public class Libro implements Comparable<Libro> {
    private int id;
    private String titulo;
    private int autor_id;
    private Date fecha_publicacion;


    public Libro(int id, String titulo, int autor_id, Date fecha_publicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor_id = autor_id;
        this.fecha_publicacion = fecha_publicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(int autor_id) {
        this.autor_id = autor_id;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", titulo=" + titulo + ", autor_id=" + autor_id + ", fecha_publicacion=" + fecha_publicacion + '}';
    }


    @Override
    public int compareTo(Libro l1) {
        return getTitulo().compareTo(l1.getTitulo());
    }
}
