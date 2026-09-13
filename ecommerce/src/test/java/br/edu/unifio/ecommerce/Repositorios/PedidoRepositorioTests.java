package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;

@SpringBootTest
public class PedidoRepositorioTests {
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Test
    public void deveBuscarUmPedidoPorId() {
        Pedido pedido = pedidoRepositorio.findById(2).orElseThrow();

        assertNotNull(pedido);
        assertEquals("PAGO", pedido.getStatus());

    }

}
