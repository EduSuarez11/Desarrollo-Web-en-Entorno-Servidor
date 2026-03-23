package es.daw.jakarta.cabeceras.service;

import es.daw.jakarta.cabeceras.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    public List<Producto> findAll();

    public Optional<Producto> findById(Long id);

    public Producto save(Producto producto);
}
