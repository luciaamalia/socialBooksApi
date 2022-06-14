package com.lucia.socialBooksApi.services.exceptions;

public class LivroNaoEncontradoException extends RuntimeException{

    public LivroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public LivroNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
