package com.project.extension.service;

import com.project.extension.dto.dashboard.EstoqueCriticoResponseDto;
import com.project.extension.dto.dashboard.ProximosAgendamentosResponseDto;
import com.project.extension.entity.Agendamento;
import com.project.extension.repository.AgendamentoRepository;
import com.project.extension.repository.EstoqueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DashboardService {

    private EstoqueRepository estoqueRepository;
    private AgendamentoRepository agendamentoRepository;

    public int getItensAbaixoMinimo() {
        return estoqueRepository.countItensAbaixoMinimo();
    }

    public int qtdServicosHoje(){
        return agendamentoRepository.countServicosHoje();
    }

    public List<EstoqueCriticoResponseDto> estoqueCritico(){
        return estoqueRepository.estoqueCritco();
    }

    public int getQtdAgendamentosHoje(){
        return agendamentoRepository.countQtdAgendamentosHoje();
    }

    public int getQtdAgendamentosFuturos(){
        return agendamentoRepository.countQtdAgendamentosFuturos();
    }

    public Double taxaOcupacaoServicos(){
        return agendamentoRepository.taxaOcupacaoServicos();
    }

    public List<ProximosAgendamentosResponseDto> proximosAgendamentos() {
        return agendamentoRepository.proximosAgendamentos();
    }
}
