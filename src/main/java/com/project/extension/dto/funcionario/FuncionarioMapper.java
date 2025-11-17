package com.project.extension.dto.funcionario;

import com.project.extension.entity.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public Funcionario toEntity(FuncionarioRequestDto dto) {
        Funcionario f = new Funcionario();

        f.setNome(dto.nome());
        f.setTelefone(dto.telefone());
        f.setFuncao(dto.funcao());
        f.setContrato(dto.contrato());
        f.setEscala(dto.escala());

        // STATUS → ATIVO (boolean)
        f.setAtivo(dto.status() == null || dto.status().equalsIgnoreCase("Ativo"));

        return f;
    }

    public FuncionarioResponseDto toResponse(Funcionario f) {
        return new FuncionarioResponseDto(
                f.getId(),
                f.getNome(),
                f.getTelefone(),
                f.getFuncao(),
                f.getContrato(),
                f.getEscala(),
                f.getAtivo() ? "Ativo" : "Pausado"
        );
    }
}
