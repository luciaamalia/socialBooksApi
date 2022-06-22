package com.lucia.socialBooksApi.repository;

import com.lucia.socialBooksApi.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentariosRepository extends JpaRepository<Comentario, Long> {
}
