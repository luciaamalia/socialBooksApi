package com.lucia.socialBooksApi.services;

import com.lucia.socialBooksApi.domain.Comentario;
import com.lucia.socialBooksApi.domain.Livro;
import com.lucia.socialBooksApi.repository.ComentariosRepository;
import com.lucia.socialBooksApi.repository.LivrosRepository;
import com.lucia.socialBooksApi.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LivrosService {

    @Autowired
    private LivrosRepository livrosRepository;

    @Autowired
    private ComentariosRepository comentariosRepository;

    public List<Livro> listar(){
        return livrosRepository.findAll();
    }

    public Livro buscar(Long id){
        Livro livro = livrosRepository.findById(id).get();

        if(livro == null){
            throw new LivroNaoEncontradoException("O Livro não pode ser encontrado.");

        }
        return livro;
    }
    public  Livro salvar(Livro livro) {
        livro.setId(null);
        return  livrosRepository.save(livro);
    }

    public void deletar(long id){
        try{
            livrosRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new LivroNaoEncontradoException("O livro não pode ser encontrado" );
        }
    }
    public void atualizar(Livro livro) {
        verificarExistencia(livro);
        livrosRepository.save(livro);
    }

    private void verificarExistencia(Livro livro) {
        buscar(livro.getId());
    }

    public Comentario salvarComentario(Long livroId, Comentario comentario) {
        Livro livro = buscar(livroId);

        comentario.setLivro(livro);
        comentario.setData(new Date());

        return comentariosRepository.save(comentario);
    }

    public List<Comentario> listarComentarios(Long livroId) {
        Livro livro = buscar(livroId);

        return livro.getComentarios();
    }

}
