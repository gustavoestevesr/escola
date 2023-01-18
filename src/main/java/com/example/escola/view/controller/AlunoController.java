package com.example.escola.view.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin
public class AlunoController {
    @Autowired
    AlunoService servico;

    @GetMapping
    public ResponseEntity<List<AlunoDto>> obterAlunos() {
      return new ResponseEntity<>(servico.obterTodos(), HttpStatus.ACCEPTED); 
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
        servico.excluirAlunoPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoDto> atualizarAluno(@PathVariable String id, @RequestBody @Valid AlunoDto AlunoDto) {
        AlunoDto dto = new ModelMapper().map(AlunoDto, AlunoDto.class);
        Optional<AlunoDto> pess = servico.atualizarAlunoPorId(id, dto);

        if (pess.isPresent()) {
            return new ResponseEntity<>(pess.get(), HttpStatus.OK);
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
    } */
}
