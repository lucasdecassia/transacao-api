package com.java.transacao_api.business.services;

import com.java.transacao_api.business.services.validacao.Validacao;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.infrastrutures.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    private final List<Validacao> validacoes;

    public void adicionarTransacoes(TransacaoRequestDTO dto) {
        log.info("Iniciado o processmento de gravar transações " + dto);

        validacoes.forEach(v -> v.validar(dto));
        listaTransacoes.add(dto);
        log.info("Transação gravada com sucesso");
    }

    public void limparTransacoes(){
        log.info("Limpando transaçoes recentes");
        listaTransacoes.clear();
        log.info("Transações limpas com sucesso");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca){
        log.info("Iniciado busca de transações por tempo: " + intervaloBusca);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        log.info("Retorno de transações com sucesso");
        return listaTransacoes.stream()
                .filter(transacao -> transacao.dataHora()
                        .isAfter(dataHoraIntervalo)).toList();
    }

}
