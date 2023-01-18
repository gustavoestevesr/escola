package com.example.escola.shared;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AlunoDto {
    private String id;
    @NotEmpty(message = "O nome deve ser preenchido")
    @NotBlank(message = "Nome não pode ter apenas espaços em branco")
    @Size(min =3, message = "Nome tem que ter pelo menos 3 caracteres")
    private String nome;
    @NotEmpty(message = "O sobrenome deve ser preenchido")
    @NotBlank(message = "Sobrenome não pode ter apenas espaços em branco")
    private String sobrenome;
    @Positive(message = "Idade deve ser maior que zero")
    private int idade;
    private String observacoes;
}
