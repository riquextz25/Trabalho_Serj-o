package br.edu.unifio.ecommerce.recursos;

import org.springframework.web.bind.annotation.RestController;

import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("produtos")

public class ProdutoRecurso {
    public List<Produto> listar ()

}
