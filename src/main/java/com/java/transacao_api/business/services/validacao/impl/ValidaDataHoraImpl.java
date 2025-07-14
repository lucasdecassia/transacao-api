package com.java.transacao_api.business.services.validacao.impl;

import com.java.transacao_api.business.services.validacao.Validacao;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.infrastrutures.exceptions.UnprocessableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@Slf4j
public class ValidaDataHoraImpl implements Validacao {

    @Override
    public void validar(TransacaoRequestDTO dto) {

        boolean isFuture = dto.dataHora().isAfter(OffsetDateTime.now());

        if(isFuture){
            log.error("Data e Hora maiores que a data e hora atual");
            throw new UnprocessableEntity("Data e Hora maiores que a data e hora atuais");
        }
    }

}
