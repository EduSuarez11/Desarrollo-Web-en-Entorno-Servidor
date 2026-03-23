package es.daw.jakarta.cabeceras.service;

import es.daw.jakarta.cabeceras.model.Producto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService{

    private static List<Producto> productos = new ArrayList<Producto>();

    @Override
    public List<Producto> findAll() {
        // Introducir productos
        // Simular que buscamos productos en una base de datos...
        return Arrays.asList(new Producto(1L, "notebook", "informática", 175000),
                new Producto(2L, "mesa escritorio", "oficina", 10000000),
                new Producto(3L, "teclado", "informatica", 400000));

    }

    @Override
    public Optional<Producto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Producto save(Producto producto) {
        return null;
    }
}
