package com.example.escola.view.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.escola.service.AlunoService;
import com.example.escola.shared.AlunoDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest()
public class AlunoControllerTest {

    @MockBean
    private AlunoService servico;
    
    @Autowired
    private AlunoController controlador;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    @DisplayName("Retorna o status OK para a listagem de alunos com conteúdo")
    public void deve_retornar_status_200_para_listagem_de_alunos() throws Exception {
        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes(null);
        
        List<AlunoDto> alunos = List.of(aluno, aluno, aluno);        

        Mockito.when(this.servico.obterTodos()).thenReturn(alunos);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alunos"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Retorna o status No Content para a listagem de alunos sem conteúdo")
    public void deve_retornar_status_204_para_listagem_de_alunos_vazia() throws Exception {        
        Mockito.when(this.servico.obterTodos()).thenReturn(List.of());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alunos"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Retorna status Found por encontrar um aluno por ID")
    public void deve_retornar_status_302_por_obter_aluno_por_id() throws Exception {

        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes(null);

        Optional<AlunoDto> alunoDtoOptional = Optional.of(aluno);
        
        Mockito.when(this.servico.obterAlunoPorId(anyString())).thenReturn(alunoDtoOptional);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alunos/007"))
                    .andExpect(MockMvcResultMatchers.status().isFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("James"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome").value("Bond"));
    }

    @Test
    @DisplayName("Retorna status Not Found por não encontrar uma aluno por ID")
    public void deve_retornar_status_404_ao_falhar_em_obter_aluno_por_id() throws Exception {
   
        Mockito.when(this.servico.obterAlunoPorId(anyString())).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alunos/007"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Retorna status Not Acceptable por não atualizar uma aluno por ID")
    public void deve_retornar_status_406_ao_falhar_em_atualizar_aluno_por_id() throws Exception{

        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes(null);

        Optional<AlunoDto> alunoDtoOptional = Optional.empty();  
        
        ObjectMapper map = new ObjectMapper();
        String body = map.writeValueAsString(aluno);

        Mockito.when(servico.atualizarAlunoPorId(anyString(), any())).thenReturn(alunoDtoOptional);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/alunos/{id}", 005)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());                         
    }

    @Test
    @DisplayName("Retorna status Ok por atualizar uma aluno por ID")
    public void deve_retornar_status_200_ao_atualizar_aluno_por_id() throws Exception{

        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes(null);

        Optional<AlunoDto> alunoDtoOptional = Optional.of(aluno);  
        
        ObjectMapper map = new ObjectMapper();
        String body = map.writeValueAsString(aluno);

        Mockito.when(servico.atualizarAlunoPorId(anyString(), any())).thenReturn(alunoDtoOptional);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/alunos/{id}", 007)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("007"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("James"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome").value("Bond"));
       
    }

    @Test
    @DisplayName("Retorna status Not Content por excluir aluno por ID")
    public void deve_retornar_status_204_ao_excluir_aluno_por_id() throws Exception{

        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes(null);

        Optional<AlunoDto> alunoDtoOptional = Optional.of(aluno);
        
        Mockito.when(this.servico.obterAlunoPorId(anyString())).thenReturn(alunoDtoOptional);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/alunos/{id}", 007))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Retorna status Not Found por não excluir um aluno por ID")
    public void deve_retornar_status_404_ao_falhar_em_excluir_aluno_por_id() throws Exception{        

        Optional<AlunoDto> alunoDtoOptional = Optional.empty();
        
        Mockito.when(this.servico.obterAlunoPorId(anyString())).thenReturn(alunoDtoOptional);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/alunos/{id}", 007))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Retorna status Created por criar um aluno por ID")
    public void deve_retornar_status_201_ao_criar_aluno() throws Exception{
    
        AlunoDto aluno = new AlunoDto();
        aluno.setId("007");
        aluno.setIdade(24);
        aluno.setNome("James");
        aluno.setSobrenome("Bond");
        aluno.setObservacoes("Ótimo aluno");        

        ObjectMapper map = new ObjectMapper();
        String json = map.writeValueAsString(aluno);                

        Mockito.when(this.servico.cadastrarAluno(ArgumentMatchers.any())).thenReturn(aluno);        

        mockMvc.perform(MockMvcRequestBuilders.post("/api/alunos/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("007"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("James"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome").value("Bond"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.observacoes").value("Ótimo aluno"));                           
    }

}
