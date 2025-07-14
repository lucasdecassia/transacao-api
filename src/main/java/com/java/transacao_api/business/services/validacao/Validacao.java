package com.java.transacao_api.business.services.validacao;

import com.java.transacao_api.controller.dtos.TransacaoRequestDTO;

public interface Validacao {

    void validar(TransacaoRequestDTO dto);
}
