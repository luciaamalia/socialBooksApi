package com.lucia.socialBooksApi.resources;

import com.lucia.socialBooksApi.domain.Livro;
import com.lucia.socialBooksApi.repository.LivrosRepository;
import com.lucia.socialBooksApi.services.LivrosService;
import com.lucia.socialBooksApi.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

    @Autowired //procura implementação pra essa variavel
    private LivrosService livrosService; //


    //ao acessar esse metodo pelo get http ira retornar esta string
        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<List<Livro>> listar(){
            return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
        }

        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<Void> salvar(@RequestBody Livro livro){
            livro = livrosService.salvar(livro);          // pegue as info e coloque dentro desse livro;

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(livro.getId()).toUri();

            return ResponseEntity.created(uri).build();

        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
            Livro livro = null; // se ele buscar o livro e não encontrá-lo ele cai no erro 404 se nao 200 OK
            try {
                livro = livrosService.buscar(id);
            } catch (LivroNaoEncontradoException e){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(livro);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
            try {
                livrosService.deletar(id);
            } catch (EmptyResultDataAccessException e) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
        public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
            livro.setId(id); //garantir q vai atualizar
            try{
                livrosService.atualizar(livro); //verifica se ele existe e att caso contrario manda um not found
            }catch (LivroNaoEncontradoException e){
                return ResponseEntity.notFound().build();
            }
           livrosService.atualizar(livro);

            return ResponseEntity.noContent().build();
        }
}
