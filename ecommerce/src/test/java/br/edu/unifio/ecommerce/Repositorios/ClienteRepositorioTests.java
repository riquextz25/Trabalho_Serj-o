package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;

@SpringBootTest
public class ClienteRepositorioTests {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    public void deveBuscarUmClientePorId() {
        Cliente cliente = clienteRepositorio.findById(2).orElseThrow();

        assertNotNull(cliente);
        assertEquals("Maria Santos", cliente.getNome());

    }

}