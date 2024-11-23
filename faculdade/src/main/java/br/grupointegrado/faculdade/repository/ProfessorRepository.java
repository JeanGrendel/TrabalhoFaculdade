package br.grupointegrado.faculdade.repository;

import br.grupointegrado.faculdade.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
