package br.grupointegrado.faculdade.controller;

import br.grupointegrado.faculdade.dto.AlunoRequestDTO;
import br.grupointegrado.faculdade.model.Aluno;
import br.grupointegrado.faculdade.model.Matricula;
import br.grupointegrado.faculdade.model.Nota;
import br.grupointegrado.faculdade.repository.AlunoRepository;
import br.grupointegrado.faculdade.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public Aluno save(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setData_nascimento(dto.data_nascimento());

        return this.repository.save(aluno);
    }

    @PutMapping("/{id}")
    public Aluno update(@PathVariable Integer id,
                        @RequestBody AlunoRequestDTO dto) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setData_nascimento(dto.data_nascimento());

        return this.repository.save(aluno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        this.repository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/relatorio")
    public ResponseEntity<Map<String, Object>> findNotasById(@PathVariable Integer id) {
        Optional<Aluno> aluno = this.repository.findById(id);

        if (aluno.isPresent()) {
            Map<String, Object> response = new HashMap<>();

            response.put("nome", aluno.get().getNome());

            List<Map<String, Object>> notasList = new ArrayList<>();

            for (Matricula matricula : aluno.get().getMatriculas()) {
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
