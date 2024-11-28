package br.grupointegrado.faculdade.controller;

import br.grupointegrado.faculdade.dto.MatriculaRequestDTO;
import br.grupointegrado.faculdade.dto.TurmaRequestDTO;
import br.grupointegrado.faculdade.model.Aluno;
import br.grupointegrado.faculdade.model.Matricula;
import br.grupointegrado.faculdade.model.Turma;
import br.grupointegrado.faculdade.repository.AlunoRepository;
import br.grupointegrado.faculdade.repository.MatriculaRepository;
import br.grupointegrado.faculdade.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaRepository repository;

    @GetMapping
    public ResponseEntity<List<Matricula>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrado."));
        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public Matricula save(@RequestBody Matricula matricula) {
        return repository.save(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Integer id, @RequestBody Matricula matriculaDetails) {
        Optional<Matricula> optionalMatricula = this.repository.findById(id);

        if (optionalMatricula.isPresent()) {
            Matricula matricula = optionalMatricula.get();
            matricula.setAlunoId(optionalMatricula.get().getAlunoId());
            matricula.setTurmaId(optionalMatricula.get().getTurmaId());

            this.repository.save(matricula);
            return ResponseEntity.ok(matricula);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        this.repository.delete(matricula);
        return ResponseEntity.noContent().build();
    }
}
