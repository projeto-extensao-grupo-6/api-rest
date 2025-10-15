package com.project.extension.controller.agendamento;

import com.project.extension.dto.agendamento.AgendamentoMapper;
import com.project.extension.dto.agendamento.AgendamentoRequestDto;
import com.project.extension.dto.agendamento.AgendamentoResponseDto;
import com.project.extension.dto.endereco.EnderecoMapper;
import com.project.extension.entity.Agendamento;
import com.project.extension.entity.Endereco;
import com.project.extension.service.AgendamentoService;
import com.project.extension.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoControllerImpl implements AgendamentoControllerDoc{

    private final AgendamentoService service;
    private final AgendamentoMapper mapper;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoService enderecoService;

    @Override
    public ResponseEntity<AgendamentoResponseDto> salvar(AgendamentoRequestDto request) {
        Endereco endereco = enderecoMapper.toEntity(request.Endereco());
        Endereco enderecoSalvo = enderecoService.cadastrar(endereco);

        Agendamento agendamento = mapper.toEntity(request);
        agendamento.setEndereco(enderecoSalvo);

        Agendamento agendamentoSalvo = service.salvar(agendamento);
        return ResponseEntity.status(201).body(mapper.toResponse(agendamentoSalvo));
    }

    @Override
    public ResponseEntity<AgendamentoResponseDto> buscarPorId(Integer id) {
        Agendamento agendamento = service.buscarPorId(id);

        return ResponseEntity.status(200).body(mapper.toResponse(agendamento));
    }

    @Override
    public ResponseEntity<List<AgendamentoResponseDto>> buscarTodos() {
        List<Agendamento> agendamentos = service.buscarTodos();

        return agendamentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(agendamentos.stream()
                    .map(mapper::toResponse)
                    .toList());
    }

    @Override
    public ResponseEntity<AgendamentoResponseDto> atualizar(AgendamentoRequestDto request, Integer id) {
        Agendamento agendamentoExistente = service.buscarPorId(id);

        Endereco enderecoAtualizado = enderecoMapper.toEntity(request.Endereco());
        enderecoAtualizado.setId(agendamentoExistente.getEndereco().getId());
        enderecoService.editar(enderecoAtualizado, enderecoAtualizado.getId());

        Agendamento agendamentoAtualizado = mapper.toEntity(request);
        agendamentoAtualizado.setEndereco(enderecoAtualizado);
        agendamentoAtualizado = service.editar(agendamentoAtualizado, id);

        return ResponseEntity.status(200).body(mapper.toResponse(agendamentoAtualizado));
    }

    @Override
    public ResponseEntity<String> deletar(Integer id) {
        Agendamento agendamento = service.buscarPorId(id);
        Integer idEndereco = agendamento.getEndereco().getId();

        service.deletar(id);
        enderecoService.deletar(idEndereco);

        return ResponseEntity.ok("Agendamento e endereço deletados com sucesso.");
    }
}
