package com.lucia.socialBooksApi.repository;

import com.lucia.socialBooksApi.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivrosRepository extends JpaRepository<Livro, Long>{//camada
}
