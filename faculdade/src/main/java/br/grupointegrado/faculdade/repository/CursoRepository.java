package br.grupointegrado.faculdade.repository;

import br.grupointegrado.faculdade.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
