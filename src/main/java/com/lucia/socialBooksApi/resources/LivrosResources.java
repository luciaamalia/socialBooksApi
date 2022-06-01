package com.lucia.socialBooksApi.resources;

import com.lucia.socialBooksApi.domain.Livro;
import com.lucia.socialBooksApi.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private LivrosRepository livrosRepository; //


    //ao acessar esse metodo pelo get http ira retornar esta string
        @RequestMapping(method = RequestMethod.GET)
        public List<Livro> listar(){
            return livrosRepository.findAll();
        }

        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<Object> salvar(@RequestBody Livro livro){
            livrosRepository.save(livro);// pegue as info e coloque dentro desse livro

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(livro.getId()).toUri();

            return ResponseEntity.created(uri).build();

        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
            Livro livro = livrosRepository.findById(id).get(); // se ele buscar o livro e não encontrá-lo ele cai no erro 404 se nao 200 OK

            if (livro == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(livro);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public void deletar(@PathVariable("id") Long id){
             livrosRepository.deleteById(id);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
        public void atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
            livro.setId(id);
            livrosRepository.save(livro);
        }
}
