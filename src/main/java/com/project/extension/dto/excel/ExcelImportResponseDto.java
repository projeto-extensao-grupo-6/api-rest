package com.project.extension.dto.excel;

public record ExcelImportResponseDto(
        int totalLinhas,
        int inseridos,
        int ignorados,
        String mensagem
) {}
