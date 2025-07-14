package com.java.transacao_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.transacao_api.business.services.TransacaoService;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.controller.transacao.TransacaoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @InjectMocks
    TransacaoController controller;

    @Mock
    TransacaoService service;

    TransacaoRequestDTO transacao;

    MockMvc mockMvc;

    @Autowired
    final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        transacao = new TransacaoRequestDTO(100.0, OffsetDateTime.of(2025, 2, 18, 14, 30, 0, 0, ZoneOffset.UTC));
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveAdicionarTransacoesComSucesso() throws Exception {

        doNothing().when(service).adicionarTransacoes(transacao);

        mockMvc.perform(post("/transacao")
                        .content(objectMapper.writeValueAsString(transacao))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
