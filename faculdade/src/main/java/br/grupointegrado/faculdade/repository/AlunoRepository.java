package br.grupointegrado.faculdade.repository;

import br.grupointegrado.faculdade.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
}
