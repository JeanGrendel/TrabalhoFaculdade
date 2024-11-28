package br.grupointegrado.faculdade.controller;

import br.grupointegrado.faculdade.model.Nota;
import br.grupointegrado.faculdade.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaRepository repository;

    @GetMapping
    public List<Nota> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> findById(@PathVariable Integer id) {
        Optional<Nota> nota = repository.findById(id);
        if (nota.isPresent()) {
            return ResponseEntity.ok(nota.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Nota> save(@RequestBody Nota nota) {
        Nota notaSalva = repository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody Nota notaDetails) {
        Optional<Nota> optionalNota = repository.findById(id);

        if (optionalNota.isPresent()) {
            Nota nota = optionalNota.get();
            nota.setMatricula(notaDetails.getMatricula());
            nota.setDisciplina(notaDetails.getDisciplina());
            nota.setNota(notaDetails.getNota());
            nota.setData_lancamento(notaDetails.getData_lancamento());

            repository.save(nota);
            return ResponseEntity.ok(nota);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Nota> optionalNota = repository.findById(id);

        if (optionalNota.isPresent()) {
            repository.delete(optionalNota.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
