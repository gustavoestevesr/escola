package com.example.escola.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Aluno {
    @Id
    private String id;
    private String nome;
    private String sobrenome;
    private int idade;
    private String observacoes;
}
