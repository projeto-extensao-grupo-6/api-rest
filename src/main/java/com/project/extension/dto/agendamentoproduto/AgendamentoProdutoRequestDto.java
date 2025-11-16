package com.project.extension.dto.agendamentoproduto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AgendamentoProdutoRequestDto(
        @Positive Integer produtoId,
        @Positive Integer quantidadeUtilizada,
        @Positive Integer quantidadeReservada
) {
}
