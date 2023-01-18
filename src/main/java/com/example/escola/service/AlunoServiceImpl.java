package com.example.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.escola.model.Aluno;
import com.example.escola.repository.AlunoRepository;
import com.example.escola.shared.AlunoDto;

@Service
public class AlunoServiceImpl implements AlunoService {
    @Autowired
    AlunoRepository repositorio;
    
    @Override
    public List<AlunoDto> obterTodos() {
        // TODO Auto-generated method stub
        List<Aluno> alunos = repositorio.findAll();

        return alunos.stream()
            .map(p -> new ModelMapper().map(p, AlunoDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AlunoDto> obterAlunoPorId(String id) {
        // TODO Auto-generated method stub
        Optional<Aluno> aluno = repositorio.findById(id);

        if (aluno.isPresent()) {
            return Optional.of(new ModelMapper().map(aluno.get(), AlunoDto.class));
        }
        return Optional.empty();
    }

    @Override
    public AlunoDto cadastrarAluno(AlunoDto alunoDto) {
        // TODO Auto-generated method stub
        ModelMapper mapper = new ModelMapper();
        Aluno alunoParaSalvar = mapper.map(alunoDto, Aluno.class); 
        alunoParaSalvar = repositorio.save(alunoParaSalvar);
        return mapper.map(alunoParaSalvar, AlunoDto.class);  
    }

    @Override
    public void excluirAlunoPorId(String id) {
        // TODO Auto-generated method stub
        repositorio.deleteById(id);
    }

    @Override
    public Optional<AlunoDto> atualizarAlunoPorId(String id, AlunoDto alunoDto) {
        // TODO Auto-generated method stub
        ModelMapper mapper = new ModelMapper();
        Optional<Aluno> aluno = repositorio.findById(id);
        Aluno alunoParaSalvar = mapper.map(alunoDto, Aluno.class);

        if (aluno.isPresent()) {
            alunoParaSalvar.setId(id);
            alunoParaSalvar = repositorio.save(alunoParaSalvar);
            return Optional.of(mapper.map(alunoParaSalvar, AlunoDto.class));
        }        
        return Optional.empty();  
    }
    
}
