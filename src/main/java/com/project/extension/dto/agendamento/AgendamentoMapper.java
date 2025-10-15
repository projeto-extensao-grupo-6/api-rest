package com.project.extension.dto.agendamento;

import com.project.extension.dto.endereco.EnderecoMapper;
import com.project.extension.entity.Agendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgendamentoMapper {

    private final EnderecoMapper enderecoMapper;

    public Agendamento toEntity(AgendamentoRequestDto dto) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento(
                dto.tipoAgendamento(),
                dto.dataAgendamento(),
                dto.statusAgendamento(),
                dto.observacao()
        );

        agendamento.setEndereco(enderecoMapper.toEntity(dto.Endereco()));

        return agendamento;
    }

    public AgendamentoResponseDto toResponse(Agendamento agendamento) {
        if (agendamento == null) return null;

        return new AgendamentoResponseDto(
                agendamento.getId(),
                agendamento.getTipoAgendamento(),
                agendamento.getDataAgendamento(),
                agendamento.getStatusAgendamento(),
                agendamento.getObservacao(),
                enderecoMapper.toResponse(agendamento.getEndereco())
        );
    }
}