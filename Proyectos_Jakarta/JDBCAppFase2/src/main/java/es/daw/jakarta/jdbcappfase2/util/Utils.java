package es.daw.jakarta.jdbcappfase2.util;


import es.daw.jakarta.jdbcappfase2.model.Fabricante;

import java.util.List;

public class Utils {
    public static String obtenerNombreFabricante(List<Fabricante> fabricantes, Integer codigo){
//        // FORMA 1: IMPERATIVA
//        for (Fabricante fab : fabricantes){
//            if (fab.getCodigo().equals(codigo)){
//                return fab.getNombre();
//            }
//        }
//        return "Desconocido";

        // FORMA 2: STREAM() + Programación funcional
        return fabricantes.stream()
                .filter(f -> f.getCodigo().equals(codigo))
                .findFirst()
                .map(Fabricante::getNombre)
                //.get();
                .orElse("Desconocido");
    }
}
