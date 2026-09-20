package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;

@SpringBootTest
public class ClienteRepositorioTests {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private Cliente novoCliente(String nome, String email, String telefone) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        return cliente;
    }

    @Test
    public void deveInserirUmCliente() {
        Cliente salvo = clienteRepositorio.save(novoCliente("Paulo Mendes", "paulo@gmail.com", "15999990001"));

        assertNotNull(salvo.getId());
        assertTrue(clienteRepositorio.existsById(salvo.getId()));
        assertEquals("Paulo Mendes", salvo.getNome());
    }

    @Test
    public void deveBuscarUmClientePorId() {
        Cliente salvo = clienteRepositorio.save(novoCliente("Fernanda Rocha", "fernanda@gmail.com", "15999990002"));

        Cliente encontrado = clienteRepositorio.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Fernanda Rocha", encontrado.getNome());
        assertEquals("fernanda@gmail.com", encontrado.getEmail());
        assertEquals("15999990002", encontrado.getTelefone());
    }

    @Test
    public void deveListarClientes() {
        long totalAntes = clienteRepositorio.count();
        Cliente a = clienteRepositorio.save(novoCliente("Cliente A", "a@teste.com", "15111111111"));
        Cliente b = clienteRepositorio.save(novoCliente("Cliente B", "b@teste.com", "15222222222"));

        List<Cliente> lista = clienteRepositorio.findAll();

        assertEquals(totalAntes + 2, lista.size());
        assertTrue(lista.stream().anyMatch(c -> c.getId().equals(a.getId()) && c.getNome().equals("Cliente A")));
        assertTrue(lista.stream().anyMatch(c -> c.getId().equals(b.getId()) && c.getNome().equals("Cliente B")));
    }

    @Test
    public void deveAlterarUmCliente() {
        Cliente salvo = clienteRepositorio.save(novoCliente("Bruno Alves", "bruno@gmail.com", "15333333333"));
        long totalAntes = clienteRepositorio.count();

        Cliente existente = clienteRepositorio.findById(salvo.getId()).orElseThrow();
        existente.setEmail("bruno.novo@gmail.com");
        clienteRepositorio.save(existente);

        Cliente alterado = clienteRepositorio.findById(salvo.getId()).orElseThrow();
        assertEquals("bruno.novo@gmail.com", alterado.getEmail());
        assertEquals("Bruno Alves", alterado.getNome());
        assertEquals(totalAntes, clienteRepositorio.count());
    }

    @Test
    public void deveExcluirUmCliente() {
        Cliente salvo = clienteRepositorio.save(novoCliente("Para Excluir", "excluir@teste.com", "15444444444"));
        Integer id = salvo.getId();
        assertTrue(clienteRepositorio.existsById(id));

        clienteRepositorio.deleteById(id);

        assertFalse(clienteRepositorio.existsById(id));
    }
}
