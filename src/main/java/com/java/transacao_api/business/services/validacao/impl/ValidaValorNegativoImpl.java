package com.java.transacao_api.business.services.validacao.impl;

import com.java.transacao_api.business.services.validacao.Validacao;
import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.java.transacao_api.infrastrutures.exceptions.UnprocessableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidaValorNegativoImpl implements Validacao{

    @Override
    public void validar(TransacaoRequestDTO dto) {
        if (dto.valor() < 0){
            log.error("Valor não pode ser menor que 0");
            throw new UnprocessableEntity("Campo Valor não pode ser negativo");
        }
    }

}
