package br.grupointegrado.faculdade.repository;

import br.grupointegrado.faculdade.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
}
