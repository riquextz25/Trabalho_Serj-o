package br.edu.unifio.ecommerce.Repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Pagamento;
import br.edu.unifio.ecommerce.repositorios.PagamentoRepositorio;

@SpringBootTest
public class PagamentoRepositorioTests {
    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Test
    public void deveBuscarUmPagamentoPorId() {
        Pagamento pagamento = pagamentoRepositorio.findById(2).orElseThrow();

        assertNotNull(pagamento);
        assertEquals("CARTAO_CREDITO", pagamento.getTipo());

    }

}
