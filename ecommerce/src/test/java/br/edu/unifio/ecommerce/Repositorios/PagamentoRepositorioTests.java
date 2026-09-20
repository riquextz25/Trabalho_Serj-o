package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.Pagamento;
import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;
import br.edu.unifio.ecommerce.repositorios.PagamentoRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;

@SpringBootTest
public class PagamentoRepositorioTests {

    private static final LocalDateTime DATA = LocalDateTime.of(2026, 9, 20, 11, 0, 0);

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Cada pagamento precisa de um pedido próprio (relacionamento 1 para 1)
    private Pedido criarPedido(String nomeCliente) {
        Cliente cliente = new Cliente();
        cliente.setNome(nomeCliente);
        cliente.setEmail(nomeCliente.toLowerCase().replace(" ", ".") + "@teste.com");
        cliente.setTelefone("15977776666");
        cliente = clienteRepositorio.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(DATA);
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        pedido.setValorTotal(new BigDecimal("300.00"));
        pedido.setCliente(cliente);
        return pedidoRepositorio.save(pedido);
    }

    private Pagamento novoPagamento(String valor, String status, String tipo, Pedido pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(new BigDecimal(valor));
        pagamento.setData(DATA);
        pagamento.setStatus(status);
        pagamento.setTipo(tipo);
        pagamento.setPedido(pedido);
        return pagamento;
    }

    @Test
    public void deveInserirUmPagamento() {
        Pedido pedido = criarPedido("Cliente Pag Insere");

        Pagamento salvo = pagamentoRepositorio.save(novoPagamento("300.00", "PENDENTE", "PIX", pedido));

        assertNotNull(salvo.getId());
        assertTrue(pagamentoRepositorio.existsById(salvo.getId()));
        assertEquals("PIX", salvo.getTipo());
        assertEquals(pedido.getId(), salvo.getPedido().getId()); // relacionamento
    }

    @Test
    public void deveBuscarUmPagamentoPorId() {
        Pedido pedido = criarPedido("Cliente Pag Busca");
        Pagamento salvo = pagamentoRepositorio.save(novoPagamento("300.00", "CONFIRMADO", "BOLETO", pedido));

        Pagamento encontrado = pagamentoRepositorio.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("BOLETO", encontrado.getTipo());
        assertEquals("CONFIRMADO", encontrado.getStatus());
        assertEquals(0, new BigDecimal("300.00").compareTo(encontrado.getValor()));
        assertEquals(pedido.getId(), encontrado.getPedido().getId());
    }

    @Test
    public void deveListarPagamentos() {
        Pedido pedido1 = criarPedido("Cliente Pag Lista Um");
        Pedido pedido2 = criarPedido("Cliente Pag Lista Dois");
        long totalAntes = pagamentoRepositorio.count();
        Pagamento a = pagamentoRepositorio.save(novoPagamento("100.00", "PENDENTE", "PIX", pedido1));
        Pagamento b = pagamentoRepositorio.save(novoPagamento("200.00", "CONFIRMADO", "CARTAO_CREDITO", pedido2));

        List<Pagamento> lista = pagamentoRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(a.getId()) && p.getTipo().equals("PIX")));
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(b.getId()) && p.getTipo().equals("CARTAO_CREDITO")));
    }

    @Test
    public void deveAlterarUmPagamento() {
        Pedido pedido = criarPedido("Cliente Pag Altera");
        Pagamento salvo = pagamentoRepositorio.save(novoPagamento("300.00", "PENDENTE", "PIX", pedido));
        long totalAntes = pagamentoRepositorio.count();

        Pagamento existente = pagamentoRepositorio.findById(salvo.getId()).orElseThrow();
        existente.setStatus("CONFIRMADO");
        pagamentoRepositorio.save(existente);

        Pagamento alterado = pagamentoRepositorio.findById(salvo.getId()).orElseThrow();
        assertEquals("CONFIRMADO", alterado.getStatus());
        assertEquals("PIX", alterado.getTipo());
        assertEquals(totalAntes, pagamentoRepositorio.count());
    }

     @Test
    @Transactional // necessário: o Pedido guarda uma referência de volta ao Pagamento
    public void deveExcluirUmPagamento() {
        Pedido pedido = criarPedido("Cliente Pag Exclui");
        Pagamento salvo = pagamentoRepositorio.save(novoPagamento("300.00", "PENDENTE", "PIX", pedido));
        Integer id = salvo.getId();
        assertTrue(pagamentoRepositorio.existsById(id));

        pagamentoRepositorio.deleteById(id);

        assertFalse(pagamentoRepositorio.existsById(id));
    }
}
