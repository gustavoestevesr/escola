package com.example.escola.service;

import java.util.List;
import java.util.Optional;

import com.example.escola.shared.AlunoDto;

public interface AlunoService {
    
    List<AlunoDto> obterTodos();
    Optional<AlunoDto> obterAlunoPorId(String id);
    AlunoDto cadastrarAluno(AlunoDto aluno);
    void excluirAlunoPorId(String id);
    Optional<AlunoDto> atualizarAlunoPorId(String id, AlunoDto aluno);

}
