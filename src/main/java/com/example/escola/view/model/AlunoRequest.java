package com.example.escola.view.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AlunoRequest {
    @NotEmpty(message = "O nome deve ser preenchido")
    private String nome;
    @NotEmpty(message = "A idade deve ser preenchida")
    private int idade;
}
