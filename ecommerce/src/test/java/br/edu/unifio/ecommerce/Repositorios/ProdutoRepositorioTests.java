package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.CategoriaRepositorio;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;

@SpringBootTest
public class ProdutoRepositorioTests {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    private Categoria criarCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao("Categoria de teste");
        return categoriaRepositorio.save(categoria);
    }

    private Produto novoProduto(String nome, String preco, Categoria categoria) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao("Produto de teste");
        produto.setEstoque((short) 10);
        produto.setPreco(new BigDecimal(preco));
        produto.setCategoria(categoria);
        return produto;
    }

    @Test
    public void deveInserirUmProduto() {
        Categoria categoria = criarCategoria("Games");

        Produto salvo = produtoRepositorio.save(novoProduto("Controle de Videogame", "299.90", categoria));

        assertNotNull(salvo.getId());
        assertTrue(produtoRepositorio.existsById(salvo.getId()));
        assertEquals("Controle de Videogame", salvo.getNome());
        assertEquals(categoria.getId(), salvo.getCategoria().getId()); // relacionamento
    }

    @Test
    public void deveBuscarUmProdutoPorId() {
        Categoria categoria = criarCategoria("Informática");
        Produto salvo = produtoRepositorio.save(novoProduto("Mouse Gamer", "150.00", categoria));

        Produto encontrado = produtoRepositorio.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Mouse Gamer", encontrado.getNome());
        assertEquals(0, new BigDecimal("150.00").compareTo(encontrado.getPreco()));
        assertEquals(categoria.getId(), encontrado.getCategoria().getId());
    }

    @Test
    public void deveListarProdutos() {
        Categoria categoria = criarCategoria("Escritório");
        long totalAntes = produtoRepositorio.count();
        Produto a = produtoRepositorio.save(novoProduto("Cadeira", "450.00", categoria));
        Produto b = produtoRepositorio.save(novoProduto("Mesa", "700.00", categoria));

        List<Produto> lista = produtoRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(a.getId()) && p.getNome().equals("Cadeira")));
        assertTrue(lista.stream().anyMatch(p -> p.getId().equals(b.getId()) && p.getNome().equals("Mesa")));
    }

    @Test
    public void deveAlterarUmProduto() {
        Categoria categoria = criarCategoria("Áudio");
        Produto salvo = produtoRepositorio.save(novoProduto("Fone de Ouvido", "100.00", categoria));
        long totalAntes = produtoRepositorio.count();

        Produto existente = produtoRepositorio.findById(salvo.getId()).orElseThrow();
        existente.setPreco(new BigDecimal("89.90"));
        produtoRepositorio.save(existente);

        Produto alterado = produtoRepositorio.findById(salvo.getId()).orElseThrow();
        assertEquals(0, new BigDecimal("89.90").compareTo(alterado.getPreco()));
        assertEquals("Fone de Ouvido", alterado.getNome());
        assertEquals(totalAntes, produtoRepositorio.count());
    }

    @Test
    public void deveExcluirUmProduto() {
        Categoria categoria = criarCategoria("Diversos");
        Produto salvo = produtoRepositorio.save(novoProduto("Para Excluir", "10.00", categoria));
        Integer id = salvo.getId();
        assertTrue(produtoRepositorio.existsById(id));

        produtoRepositorio.deleteById(id);

        assertFalse(produtoRepositorio.existsById(id));
    }
}
