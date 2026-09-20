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

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.ItemPedido;
import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.CategoriaRepositorio;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;
import br.edu.unifio.ecommerce.repositorios.ItemPedidoRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;

@SpringBootTest
public class ItemPedidoRepositorioTests {

    @Autowired
    private ItemPedidoRepositorio itemPedidoRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    private Pedido criarPedido() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Item");
        cliente.setEmail("item@teste.com");
        cliente.setTelefone("15988887777");
        cliente = clienteRepositorio.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.of(2026, 9, 20, 10, 30, 0));
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        pedido.setValorTotal(new BigDecimal("500.00"));
        pedido.setCliente(cliente);
        return pedidoRepositorio.save(pedido);
    }

    private Produto criarProduto(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria " + nome);
        categoria.setDescricao("Categoria de teste");
        categoria = categoriaRepositorio.save(categoria);

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao("Produto de teste");
        produto.setEstoque((short) 5);
        produto.setPreco(new BigDecimal("100.00"));
        produto.setCategoria(categoria);
        return produtoRepositorio.save(produto);
    }

    private ItemPedido novoItem(int quantidade, String valorUnitario, Pedido pedido, Produto produto) {
        ItemPedido item = new ItemPedido();
        item.setQuantidade(quantidade);
        item.setValorUnitario(new BigDecimal(valorUnitario));
        item.setPedido(pedido);
        item.setProduto(produto);
        return item;
    }

    @Test
    public void deveInserirUmItemPedido() {
        Pedido pedido = criarPedido();
        Produto produto = criarProduto("Teclado");

        ItemPedido salvo = itemPedidoRepositorio.save(novoItem(2, "100.00", pedido, produto));

        assertNotNull(salvo.getId());
        assertTrue(itemPedidoRepositorio.existsById(salvo.getId()));
        assertEquals(2, salvo.getQuantidade());
        assertEquals(pedido.getId(), salvo.getPedido().getId());   // relacionamento com Pedido
        assertEquals(produto.getId(), salvo.getProduto().getId()); // relacionamento com Produto
    }

    @Test
    public void deveBuscarUmItemPedidoPorId() {
        Pedido pedido = criarPedido();
        Produto produto = criarProduto("Monitor");
        ItemPedido salvo = itemPedidoRepositorio.save(novoItem(3, "80.00", pedido, produto));

        ItemPedido encontrado = itemPedidoRepositorio.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals(3, encontrado.getQuantidade());
        assertEquals(0, new BigDecimal("80.00").compareTo(encontrado.getValorUnitario()));
        assertEquals(pedido.getId(), encontrado.getPedido().getId());
        assertEquals(produto.getId(), encontrado.getProduto().getId());
    }

    @Test
    public void deveListarItensPedido() {
        Pedido pedido = criarPedido();
        Produto produto = criarProduto("Webcam");
        long totalAntes = itemPedidoRepositorio.count();
        ItemPedido a = itemPedidoRepositorio.save(novoItem(1, "100.00", pedido, produto));
        ItemPedido b = itemPedidoRepositorio.save(novoItem(4, "100.00", pedido, produto));

        List<ItemPedido> lista = itemPedidoRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(i -> i.getId().equals(a.getId()) && i.getQuantidade() == 1));
        assertTrue(lista.stream().anyMatch(i -> i.getId().equals(b.getId()) && i.getQuantidade() == 4));
    }

    @Test
    public void deveAlterarUmItemPedido() {
        Pedido pedido = criarPedido();
        Produto produto = criarProduto("Headset");
        ItemPedido salvo = itemPedidoRepositorio.save(novoItem(1, "100.00", pedido, produto));
        long totalAntes = itemPedidoRepositorio.count();

        ItemPedido existente = itemPedidoRepositorio.findById(salvo.getId()).orElseThrow();
        existente.setQuantidade(5);
        itemPedidoRepositorio.save(existente);

        ItemPedido alterado = itemPedidoRepositorio.findById(salvo.getId()).orElseThrow();
        assertEquals(5, alterado.getQuantidade());
        assertEquals(salvo.getId(), alterado.getId());
        assertEquals(totalAntes, itemPedidoRepositorio.count());
    }

    @Test
    public void deveExcluirUmItemPedido() {
        Pedido pedido = criarPedido();
        Produto produto = criarProduto("Cabo HDMI");
        ItemPedido salvo = itemPedidoRepositorio.save(novoItem(1, "20.00", pedido, produto));
        Integer id = salvo.getId();
        assertTrue(itemPedidoRepositorio.existsById(id));

        itemPedidoRepositorio.deleteById(id);

        assertFalse(itemPedidoRepositorio.existsById(id));
    }
}
