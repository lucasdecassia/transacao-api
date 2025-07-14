package com.java.transacao_api.controller;

import com.java.transacao_api.business.services.EstatisticasService;
import com.java.transacao_api.business.services.TransacaoService;
import com.java.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.java.transacao_api.controller.estatisticas.EstatisticasController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(MockitoExtension.class)
class EstatisticaControllerTest {

    @InjectMocks
    EstatisticasController estatisticasController;

    @Mock
    EstatisticasService estatisticasService;
    @Mock
    TransacaoService transacaoService;

    EstatisticasResponseDTO estatisticas;

    MockMvc mockMvc;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(estatisticasController).build();
        estatisticas = new EstatisticasResponseDTO(1L, 100.0, 100.0, 100.0, 100.0);
    }

    @Test
    void calcularEstatisticaComSucesso() throws Exception {
        when(estatisticasService.calcularEstatisticasTransacoes(60)).thenReturn((estatisticas));

        mockMvc.perform(get("/estatistica")
                        .param("intervaloBusca", "60")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(estatisticas.count()));
    }

}
