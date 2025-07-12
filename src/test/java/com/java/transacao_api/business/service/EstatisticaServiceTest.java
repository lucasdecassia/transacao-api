package com.java.transacao_api.business.service;

import com.java.transacao_api.business.services.EstatisticasService;
import com.java.transacao_api.business.services.TransacaoService;
import com.java.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Estatística teste")
class EstatisticaServiceTest {

@InjectMocks
EstatisticasService estatisticasService;

@Mock
    TransacaoService transacaoService;

TransacaoRequestDTO transacao;
EstatisticasResponseDTO estatisticas;

@BeforeEach
void setUp(){
transacao = new TransacaoRequestDTO(100.0, OffsetDateTime.now());
estatisticas = new EstatisticasResponseDTO(1L, 100.0, 100.0, 100.0, 100.0);
}

@Test
@DisplayName("Deve calcular uma estatistica com sucesso")
void calcularEstatisticaComSucesso(){

        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacao));

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertEquals(estatisticas, resultado);

}

@Test
@DisplayName("Deve retornar uma lista vazia ")
void calcularEstatisticasQuandoListaVazia(){

    EstatisticasResponseDTO estatisticaEsperado = new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);

    when(transacaoService.buscarTransacoes(60))
            .thenReturn(Collections.emptyList());

    EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

    verify(transacaoService, times(1)).buscarTransacoes(60);
    Assertions.assertEquals(estatisticaEsperado, resultado);
}

}
