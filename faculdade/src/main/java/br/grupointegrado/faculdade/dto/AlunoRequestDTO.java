package br.grupointegrado.faculdade.dto;

import java.util.Date;

public record AlunoRequestDTO(String nome, String email, String matricula, Date data_nascimento) {
}
