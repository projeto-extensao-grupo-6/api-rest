package com.project.extension.controller.servico;

import com.project.extension.dto.servico.ServicoMapper;
import com.project.extension.dto.servico.ServicoRequestDto;
import com.project.extension.dto.servico.ServicoResponseDto;
import com.project.extension.entity.Servico;
import com.project.extension.service.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos")
@AllArgsConstructor
public class ServicoControllerImpl implements ServicoControllerDoc{

    private final ServicoService servicoService;
    private final ServicoMapper servicoMapper;

    @Override
    public ResponseEntity<ServicoResponseDto> cadastrarSolicitacao(ServicoRequestDto dto) {
        Servico servicoSalvar = servicoMapper.toEntity(dto);
        Servico servicoSalvo = servicoService.cadastrar(servicoSalvar);
        return ResponseEntity.status(201).body(servicoMapper.toResponse(servicoSalvo));
    }

    @Override
    public ResponseEntity<List<ServicoResponseDto>> listar() {
        List<Servico> pendentes = servicoService.listar();
        if (pendentes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ServicoResponseDto> dtos = pendentes.stream()
                .map(servicoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);

    }

    @Override
    public ResponseEntity<ServicoResponseDto> buscarServicoPorId(Integer id) {
        Servico servico = servicoService.buscarPorId(id);
        return ResponseEntity.status(200).body(servicoMapper.toResponse(servico));

    }

    @Override
    public ResponseEntity<ServicoResponseDto> editar(Integer id, ServicoRequestDto dto) {
        Servico servico = servicoMapper.toEntity(dto);
        return ResponseEntity.status(200).body(servicoMapper.toResponse(servicoService.editar(id, servico)));
    }

    @Override
    public ResponseEntity<Void> deletar(Integer id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}