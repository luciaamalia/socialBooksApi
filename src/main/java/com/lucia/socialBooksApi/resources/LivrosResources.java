package com.lucia.socialBooksApi.resources;

import com.lucia.socialBooksApi.domain.Livro;
import com.lucia.socialBooksApi.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        public void salvar(@RequestBody Livro livro){
            livrosRepository.save(livro);
            // pegue as info e coloque dentro desse livro
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public Livro buscar(@PathVariable("id") Long id) {
            return livrosRepository.findById(id).get();
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
