package com.lucia.socialBooksApi.resources;


import com.lucia.socialBooksApi.domain.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucia.socialBooksApi.domain.Livro;
import com.lucia.socialBooksApi.services.LivrosService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

    @Autowired //procura implementação pra essa variavel
    private LivrosService livrosService; //


    //ao acessar esse metodo pelo get http ira retornar esta string
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Livro>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
        livro = livrosService.salvar(livro);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(livro.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
        Livro livro = livrosService.buscar(id);
        return ResponseEntity.status(HttpStatus.OK).body(livro);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        livrosService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@RequestBody Livro livro,
                                          @PathVariable("id") Long id) {
        livro.setId(id);
        livrosService.atualizar(livro);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,
                                                    @RequestBody Comentario comentario) {

        livrosService.salvarComentario(livroId, comentario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(uri).build();
    }
}