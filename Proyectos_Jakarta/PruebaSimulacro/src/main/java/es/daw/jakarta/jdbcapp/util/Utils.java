package es.daw.jakarta.jdbcapp.util;

import es.daw.jakarta.jdbcapp.model.Fabricante;
import es.daw.jakarta.jdbcapp.model.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String obtenerNombreFabricante(List<Fabricante> fabricantes, Integer codigo) {

//        // FORMA 1: IMPERATIVA
//
//        for (Fabricante f: fabricantes) {
//            if (f.getCodigo().equals(codigo))
//                return f.getNombre();
//        }
//        return "desconocido";


        // FORMA 2: STREAM()
         return fabricantes.stream()
                 .filter(f -> f.getCodigo().equals(codigo))
                 .findFirst()
                 //.get().getNombre();
                 .map(Fabricante::getNombre)
                 .orElse("desconocido");
    }

    public static boolean existeNombreProducto(List<Producto> productos, Integer codigo) {
        boolean existe = false;
        for (Producto p: productos) {
            if (p.getCodigo_fabricante().equals(codigo)) {
                existe = true;
                return existe;
            }
        }

        return existe;
    }

}
