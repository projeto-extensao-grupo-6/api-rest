package com.project.extension.dto.dashboard;

import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    public ItensAbaixoMinimoKpiResponseDto toItensAbaixoMinimoDto(int quantidade) {
        return new ItensAbaixoMinimoKpiResponseDto(quantidade);
    }

    public AgendamentosHojeResponseDto toAgendamentosHojeDto(int qtdAgendamentosHoje){
        return new AgendamentosHojeResponseDto(qtdAgendamentosHoje);
    }
}