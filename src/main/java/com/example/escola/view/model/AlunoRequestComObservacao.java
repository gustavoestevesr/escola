package com.example.escola.view.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AlunoRequestComObservacao {
    @NotEmpty(message = "O nome deve ser preenchido")
    private String nome;
    @NotEmpty(message = "A idade deve ser preenchida")
    private int idade;
    @NotEmpty(message = "A observacao deve ser preenchida")
    private String observacoes;
}
