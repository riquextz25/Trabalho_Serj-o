package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.repositorios.CategoriaRepositorio;

@SpringBootTest
public class CategoriaRepositorioTests {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    private Categoria novaCategoria(String nome, String descricao) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);
        return categoria;
    }

    @Test
    public void deveInserirUmaCategoria() {
        Categoria salva = categoriaRepositorio.save(novaCategoria("Brinquedos", "Brinquedos e jogos"));

        assertNotNull(salva.getId());
        assertTrue(categoriaRepositorio.existsById(salva.getId()));
        assertEquals("Brinquedos", salva.getNome());
    }

    @Test
    public void deveBuscarUmaCategoriaPorId() {
        Categoria salva = categoriaRepositorio.save(novaCategoria("Pet Shop", "Produtos para animais"));

        Categoria encontrada = categoriaRepositorio.findById(salva.getId()).orElseThrow();

        assertEquals(salva.getId(), encontrada.getId());
        assertEquals("Pet Shop", encontrada.getNome());
        assertEquals("Produtos para animais", encontrada.getDescricao());
    }

    @Test
    public void deveListarCategorias() {
        long totalAntes = categoriaRepositorio.count();
        Categoria a = categoriaRepositorio.save(novaCategoria("Jardinagem", "Itens de jardim"));
        Categoria b = categoriaRepositorio.save(novaCategoria("Papelaria", "Material escolar"));

        List<Categoria> lista = categoriaRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(c -> c.getId().equals(a.getId()) && c.getNome().equals("Jardinagem")));
        assertTrue(lista.stream().anyMatch(c -> c.getId().equals(b.getId()) && c.getNome().equals("Papelaria")));
    }

    @Test
    public void deveAlterarUmaCategoria() {
        Categoria salva = categoriaRepositorio.save(novaCategoria("Automotivo", "Peças"));
        long totalAntes = categoriaRepositorio.count();

        Categoria existente = categoriaRepositorio.findById(salva.getId()).orElseThrow();
        existente.setNome("Automotivo e Acessórios");
        categoriaRepositorio.save(existente);

        Categoria alterada = categoriaRepositorio.findById(salva.getId()).orElseThrow();
        assertEquals("Automotivo e Acessórios", alterada.getNome());
        assertEquals(salva.getId(), alterada.getId()); // mesmo registro
        assertEquals(totalAntes, categoriaRepositorio.count()); // não criou registro novo
    }

    @Test
    public void deveExcluirUmaCategoria() {
        Categoria salva = categoriaRepositorio.save(novaCategoria("Para Excluir", "Será removida"));
        Short id = salva.getId();
        assertTrue(categoriaRepositorio.existsById(id));

        categoriaRepositorio.deleteById(id);

        assertFalse(categoriaRepositorio.existsById(id));
    }
}
