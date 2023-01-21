package com.example.escola.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.escola.service.AlunoService;
import com.example.escola.shared.AlunoDto;
import com.example.escola.view.model.AlunoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin
public class AlunoController {
    
    private AlunoService servico;

    public AlunoController(AlunoService servico){
        this.servico = servico;
    }

    ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> obterAlunos() {
        List<AlunoDto> alunosDto = servico.obterTodos();
        List<AlunoResponse> alunosResponse = alunosDto.stream()
        .map(a -> mapper.map(a, AlunoResponse.class))
        .collect(Collectors.toList());

        if(alunosDto.isEmpty()) {
            return new ResponseEntity<>(alunosResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(alunosResponse, HttpStatus.OK);
    } 

    @GetMapping(value = "/porta")
    public String verificarPorta(@Value("${local.server.port}") String porta) {
        return String.format("Microsserviço respondeu a partir da porta %s", porta);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlunoDto> obterAlunoPorId(@PathVariable String id) {
        Optional<AlunoDto> aluno = servico.obterAlunoPorId(id);

        if (aluno.isPresent()) {
            return new ResponseEntity<>(aluno.get(), HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);       
    }

    @PostMapping
    public ResponseEntity<AlunoDto> cadastrarAluno(@RequestBody @Valid AlunoDto AlunoDto) { 
        return new ResponseEntity<>(servico.cadastrarAluno(AlunoDto), HttpStatus.CREATED); 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirAluno(@PathVariable String id) {
        Optional<AlunoDto> aluno = servico.obterAlunoPorId(id);

        if (aluno.isPresent()) {
            servico.excluirAlunoPorId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoDto> atualizarAluno(@PathVariable String id, @RequestBody @Valid AlunoDto alunoDto) {                        
        Optional<AlunoDto> alunoDtoOptional = servico.atualizarAlunoPorId(id, alunoDto);

        if (alunoDtoOptional.isPresent()) {                        
            return new ResponseEntity<>(alunoDtoOptional.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);           
    }

    /*
    Os exemplos abaixo são para demonstrar o uso de classes específicas para os 
    dados de Request e Response. O uso desses modelos ajudam a proteger os dados da 
    aplicação, utilizando apenas os atributos que de fato precisam ser demonstrados.
    
    Utilizamos o ModelMapper junto com o DTO (Data Transfer Object) para transferir 
    os dados entre as classes. 
    
    @GetMapping
    public ResponseEntity<List<PessoaResponse>> obterPessoas() {
        List<PessoaDto> dto = servico.obterTodos();

        List<PessoaResponse> response = dto.stream()
           .map(d -> new ModelMapper().map(d, PessoaResponse.class))
           .collect(Collectors.toList());

      return new ResponseEntity<>(response, HttpStatus.ACCEPTED);  
    }

    @PostMapping
    public ResponseEntity<PessoaResponseComObservacao> cadastrarPessoa(@RequestBody PessoaRequestComObservacao pessoa) {
        ModelMapper mapper = new ModelMapper();
        PessoaDto dto = mapper.map(pessoa, PessoaDto.class);
        dto = servico.cadastrarPessoa(dto);
        PessoaResponseComObservacao pessoaResponse = mapper.map(dto, PessoaResponseComObservacao.class);
        
        return new ResponseEntity<>(pessoaResponse, HttpStatus.CREATED); 
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoDto> atualizarAluno(@PathVariable String id, @RequestBody @Valid AlunoRequest alunoRequest) {                
        AlunoDto alunoDto = new ModelMapper().map(alunoRequest, AlunoDto.class);
        Optional<AlunoDto> alunoDtoOptional = servico.atualizarAlunoPorId(id, alunoDto);

        if (alunoDtoOptional.isPresent()) {                        
            return new ResponseEntity<>(alunoDtoOptional.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);           
    }*/
}
