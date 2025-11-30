package com.project.extension.dto.pedido;

import com.project.extension.dto.cliente.ClienteRequestDto;
import com.project.extension.dto.etapa.EtapaRequestDto;
import com.project.extension.dto.status.StatusRequestDto;
import com.project.extension.entity.TipoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PedidoRequestDto(
        @Positive @NotNull BigDecimal valorTotal,
        @NotBlank Boolean ativo,
        @NotBlank String formaPagamento,
        @NotBlank TipoPedido tipoPedido,
        @NotBlank String observacao,
        @Valid ClienteRequestDto cliente,
        @Valid @NotNull StatusRequestDto status
) {
}
