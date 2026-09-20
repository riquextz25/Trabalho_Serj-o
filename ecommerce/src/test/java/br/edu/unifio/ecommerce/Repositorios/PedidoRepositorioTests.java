package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;

@SpringBootTest
public class PedidoRepositorioTests {

    // data fixa: evita diferença de precisão (nanossegundos) entre Java e MySQL
    private static final LocalDateTime DATA = LocalDateTime.of(2026, 9, 20, 10, 30, 0);

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private Cliente criarCliente(String nome) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(nome.toLowerCase().replace(" ", ".") + "@teste.com");
        cliente.setTelefone("15999999999");
        return clienteRepositorio.save(cliente);
    }

    private Pedido novoPedido(String status, String valorTotal, Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setData(DATA);
        pedido.setStatus(status);
        pedido.setValorTotal(new BigDecimal(valorTotal));
        pedido.setCliente(cliente);
        return pedido;
    }

    @Test
    public void deveInserirUmPedido() {
        Cliente cliente = criarCliente("Cliente Pedido");

        Pedido salvo = pedidoRepositorio.save(novoPedido("AGUARDANDO_PAGAMENTO", "350.00", cliente));

        assertNotNull(salvo.getId());
        assertTrue(pedidoRepositorio.existsById(salvo.getId()));
        assertEquals("AGUARDANDO_PAGAMENTO", salvo.getStatus());
        assertEquals(cliente.getId(), salvo.getCliente().getId()); // relacionamento
    }

    @Test
    public void deveBuscarUmPedidoPorId() {
        Cliente cliente = criarCliente("Cliente Busca");
        Pedido salvo = pedidoRepositorio.save(novoPedido("PAGO", "120.00", cliente));

        Pedido encontrado = pedidoRepositorio.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("PAGO", encontrado.getStatus());
        assertEquals(DATA, encontrado.getData());
        assertEquals(0, new BigDecimal("120.00").compareTo(encontrado.getValorTotal()));
        assertEquals(cliente.getId(), encontrado.getCliente().getId());
    }

    @Test
    public void deveListarPedidos() {
        Cliente cliente = criarCliente("Cliente Lista");
        long totalAntes = pedidoRepositorio.count();
        Pedido a = pedidoRepositorio.save(novoPedido("PAGO", "50.00", cliente));
        Pedido b = pedidoRepositorio.save(novoPedido("ENVIADO", "75.00", cliente));

        List<Pedido> lista = pedidoRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(a.getId()) && p.getStatus().equals("PAGO")));
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(b.getId()) && p.getStatus().equals("ENVIADO")));
    }

    @Test
    public void deveAlterarUmPedido() {
        Cliente cliente = criarCliente("Cliente Altera");
        Pedido salvo = pedidoRepositorio.save(novoPedido("AGUARDANDO_PAGAMENTO", "200.00", cliente));
        long totalAntes = pedidoRepositorio.count();

        Pedido existente = pedidoRepositorio.findById(salvo.getId()).orElseThrow();
        existente.setStatus("PAGO");
        pedidoRepositorio.save(existente);

        Pedido alterado = pedidoRepositorio.findById(salvo.getId()).orElseThrow();
        assertEquals("PAGO", alterado.getStatus());
        assertEquals(0, new BigDecimal("200.00").compareTo(alterado.getValorTotal()));
        assertEquals(totalAntes, pedidoRepositorio.count());
    }

    @Test
    public void deveExcluirUmPedido() {
        Cliente cliente = criarCliente("Cliente Exclui");
        Pedido salvo = pedidoRepositorio.save(novoPedido("PAGO", "10.00", cliente));
        Integer id = salvo.getId();
        assertTrue(pedidoRepositorio.existsById(id));

        pedidoRepositorio.deleteById(id);

        assertFalse(pedidoRepositorio.existsById(id));
    }
}
