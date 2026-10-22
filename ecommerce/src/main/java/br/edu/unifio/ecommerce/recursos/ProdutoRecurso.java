package br.edu.unifio.ecommerce.recursos;

import org.springframework.web.bind.annotation.RestController;

import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping 
    @ResponseStatus (org.springframework.http.HttpStatus.CREATED)
    public Produto inserir (@RequestBody Produto produto) { //Método para inserir
        return produtoRepositorio.save(produto);
    }

    @DeleteMapping 
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void excluir (Integer id) {
        produtoRepositorio.deleteById(id);
    }
    
    

}
