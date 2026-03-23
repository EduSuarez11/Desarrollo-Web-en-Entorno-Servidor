package es.daw.jakarta.pedidosexamen.util;

import es.daw.jakarta.pedidosexamen.model.Cliente;
import es.daw.jakarta.pedidosexamen.model.Pedido;

import java.util.List;

public class Utils {
    public static String getCliente(Long id_cliente, List<Cliente> clientes) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id_cliente)) {
                return c.getNombre();
            }
        }

        return "NO ENCONTRADO";
    }
}
