package com.java.transacao_api.business.service;

import com.java.transacao_api.business.services.TransacaoService;
import com.java.transacao_api.business.services.validacao.Validacao;
import com.java.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.infrastrutures.exceptions.UnprocessableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transação teste")
class TransacaoServiceTest {

@InjectMocks
TransacaoService transacaoService;

    Validacao validacaoValorNegativo;

    Validacao validacaoDataFutura;

    Validacao validacaoCampoPreenchido;

TransacaoRequestDTO transacao;
EstatisticasResponseDTO estatisticas;

@BeforeEach
void setUp(){
    transacao = new TransacaoRequestDTO(100.0, OffsetDateTime.now());
    estatisticas = new EstatisticasResponseDTO(1L, 100.0, 100.0, 100.0, 100.0);

    validacaoValorNegativo = dto -> {
        if(dto.valor() < 0){
            throw new UnprocessableEntity("Campo Valor não pode ser negativo");
        }
    };

    validacaoCampoPreenchido = dto -> {
        if (dto.valor() == null || dto.dataHora() == null){
            throw new UnprocessableEntity("Campos não preenchidos");
        }
    };

    validacaoDataFutura = dto -> {
        if (dto.dataHora().isAfter(OffsetDateTime.now())){
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atuais");
        }
    };

    List<Validacao> validacoes = List.of(validacaoValorNegativo, validacaoDataFutura, validacaoCampoPreenchido);
    transacaoService = new TransacaoService(validacoes);
}

@Test
void deveAdicionarTransacaoComSucesso(){
    transacaoService.adicionarTransacoes(transacao);
    List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);

    assertEquals(1, transacoes.size());
    assertEquals(transacao, transacoes.get(0));
}

    @Test
    void deveLancarExcecaoCasoValorSejaNegativo(){
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes(new TransacaoRequestDTO(-10.0, OffsetDateTime.now())));

        assertEquals("Campo Valor não pode ser negativo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoCasoDataHoraMaiorQueAtual(){
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes(new TransacaoRequestDTO(10.0, OffsetDateTime.now().plusDays(1))));

        assertEquals("Data e hora maiores que a data e hora atuais", exception.getMessage());
    }

    @Test
    void deveLimparTransacaoComSucesso(){
        transacaoService.limparTransacoes();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);

        assertTrue(transacoes.isEmpty());
    }

    @Test
    void deveBuscarTransacaoDentroDoIntervalo(){
        TransacaoRequestDTO dto = new TransacaoRequestDTO(10.00, OffsetDateTime.now().minusHours(1));

        transacaoService.adicionarTransacoes(transacao);
        transacaoService.adicionarTransacoes(dto);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(60);

        assertTrue(transacoes.contains(transacao));
        assertFalse(transacoes.contains(dto));
    }

}
