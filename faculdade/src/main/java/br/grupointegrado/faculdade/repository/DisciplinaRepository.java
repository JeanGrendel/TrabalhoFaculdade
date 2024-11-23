package br.grupointegrado.faculdade.repository;

import br.grupointegrado.faculdade.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
}
