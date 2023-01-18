package com.example.escola.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.escola.model.Aluno;

public interface AlunoRepository extends MongoRepository<Aluno, String> {
    
}
