package br.grupointegrado.faculdade.controller;

import br.grupointegrado.faculdade.dto.ProfessorRequestDTO;
import br.grupointegrado.faculdade.dto.TurmaRequestDTO;
import br.grupointegrado.faculdade.model.Matricula;
import br.grupointegrado.faculdade.model.Nota;
import br.grupointegrado.faculdade.model.Professor;
import br.grupointegrado.faculdade.model.Turma;
import br.grupointegrado.faculdade.repository.NotaRepository;
import br.grupointegrado.faculdade.repository.ProfessorRepository;
import br.grupointegrado.faculdade.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository repository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<List<Turma>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));
        return ResponseEntity.ok(turma);
    }

    @PostMapping
    public Turma save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        return this.repository.save(turma);
    }

    @PutMapping("/{id}")
    public Turma update(@PathVariable Integer id,
                        @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        return this.repository.save(turma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        this.repository.delete(turma);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/relatorio")
    public ResponseEntity<Map<String, Object>> findNotasById(@PathVariable Integer id) {
        Optional<Turma> turma = repository.findById(id);

        if (turma.isPresent()) {
            Map<String, Object> response = new HashMap<>();

            response.put("idTurma", turma.get().getId());

            List<Map<String, Object>> notasList = new ArrayList<>();

            for (Matricula matricula : turma.get().getMatriculas()) {
                List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());

                for (Nota nota : notas) {
                    Map<String, Object> notaData = new HashMap<>();
                    notaData.put("disciplina", nota.getDisciplina().getNome());  // Nome da disciplina
                    notaData.put("nota", nota.getNota());  // Nota obtida
                    notaData.put("data_lancamento", nota.getData_lancamento());  // Data de lançamento

                    notasList.add(notaData);
                }
            }
            response.put("notas", notasList);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
