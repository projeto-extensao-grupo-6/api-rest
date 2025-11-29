package com.project.extension.controller.dashboard;

import com.project.extension.dto.dashboard.AgendamentosHojeResponseDto;
import com.project.extension.dto.dashboard.DashboardMapper;
import com.project.extension.dto.dashboard.ItensAbaixoMinimoKpiResponseDto;
import com.project.extension.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardControllerImpl implements DashboardControllerDoc {

    private final DashboardService dashboardService;
    private final DashboardMapper mapper;

    @Override
    public ResponseEntity<ItensAbaixoMinimoKpiResponseDto> getItensAbaixoMinimo() {
        var dto = dashboardService.getItensAbaixoMinimo();
        return ResponseEntity.ok(mapper.toItensAbaixoMinimoDto(dto));
    }

    @Override
    public ResponseEntity<AgendamentosHojeResponseDto> getQtdAgendamentosHoje() {
        var dto = dashboardService.getAgendamentosHoje();
        return ResponseEntity.ok(mapper.toAgendamentosHojeDto(dto));
    }

}
