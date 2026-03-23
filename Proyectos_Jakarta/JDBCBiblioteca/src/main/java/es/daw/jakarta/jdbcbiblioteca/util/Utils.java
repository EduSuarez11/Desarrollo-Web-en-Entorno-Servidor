package es.daw.jakarta.jdbcbiblioteca.util;

import es.daw.jakarta.jdbcbiblioteca.model.Autor;


import java.util.List;

public class Utils {

    public static String encontrarAutor(List<Autor> autores, Integer autor_id) {
        for (Autor a : autores) {
            if (autor_id.equals(a.getId())) {
                return a.getNombre();
            }
        }
        return "Desconocido";
    }
//       return autores.stream()
//               .filter(a -> a.getId() == autor_id)
//               .sorted((a1, a2) -> a1.getNombre().compareTo(a2.getNombre()))
//               .toList();
}
