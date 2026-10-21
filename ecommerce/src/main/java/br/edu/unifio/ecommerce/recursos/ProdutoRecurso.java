package br.edu.unifio.ecommerce.recursos;

import org.springframework.web.bind.annotation.RestController;

import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;


@SuppressWarnings("unused")
@RestController 
@RequestMapping("/produtos")

public class ProdutoRecurso {
    private final ProdutoRepositorio produtoRepositorio;

    public ProdutoRecurso(ProdutoRepositorio produtoRepositorio){
        this.produtoRepositorio = produtoRepositorio;
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepositorio.findAll(Sort.by(Produto::getNome));
    }

    @GetMapping("/{id}")
    public Produto buscarPorId (Integer id) {
        return produtoRepositorio.findById(id).orElseThrow();
    }
    
    

}
