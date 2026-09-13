package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.ItemPedido;
import br.edu.unifio.ecommerce.repositorios.ItemPedidoRepositorio;

@SpringBootTest
public class ItemPedidoRepositorioTests {
    @Autowired
    private ItemPedidoRepositorio itemPedidoRepositorio;

    @Test
    public void deveBuscarUmItemPedidoPorId() {
        ItemPedido itemPedido = itemPedidoRepositorio.findById(2).orElseThrow();

        assertNotNull(itemPedido);
        assertEquals(0, BigDecimal.valueOf(120.00).compareTo(itemPedido.getValorUnitario()));

    }

}
