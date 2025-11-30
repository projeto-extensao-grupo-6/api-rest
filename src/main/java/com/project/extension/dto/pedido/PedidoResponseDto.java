package com.project.extension.dto.pedido;

import com.project.extension.dto.cliente.ClienteResponseDto;
import com.project.extension.dto.status.StatusResponseDto;
import com.project.extension.entity.TipoPedido;

import java.math.BigDecimal;

public record PedidoResponseDto(
        Integer id,
        BigDecimal valorTotal,
        Boolean ativo,
        String observacao,
        String formaPagamento,
        TipoPedido tipoPedido,
        ClienteResponseDto cliente,
        StatusResponseDto status
) {
}
