package com.java.transacao_api.business.services.validacao.impl;

import com.java.transacao_api.business.services.validacao.Validacao;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.infrastrutures.exceptions.UnprocessableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidaCampoPreenchidoImpl implements Validacao {

    @Override
    public void validar(TransacaoRequestDTO dto) {
        if(dto.valor() == null || dto.dataHora() == null){
            log.info("Os campos não foram preenchidos");
        throw new UnprocessableEntity("Campos não preenchidos");
    }
  }
}
