package com.project.extension.controller.servico;

import com.project.extension.dto.servico.ServicoRequestDto;
import com.project.extension.dto.servico.ServicoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "Operações relacionadas a criação de serviços")
public interface ServicoControllerDoc {

    @PostMapping
    @Operation(summary = "Cadastrar serviço", description = """
            Cadastrar um novo serviço
            ---
            Cadastrar um novo serviço no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServicoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Erro no corpo da requisição")
    })
    ResponseEntity<ServicoResponseDto> cadastrarSolicitacao(@RequestBody ServicoRequestDto dto);

    @GetMapping
    @Operation(summary = "Listar serviços", description = """
            Lista todas as solicitações
            ---
            Lista todas as solicitações que estão cadastrados no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existe solicitações no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServicoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existe nenhum serviço no banco de dados")
    })
    ResponseEntity<List<ServicoResponseDto>> listar();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por Id", description = """
            Buscar serviço por Id
            ---
            Buscar serviço por Id no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando a serviço existe no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServicoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<ServicoResponseDto> buscarServicoPorId(@PathVariable Integer id);

    @PutMapping("/{id}")
    @Operation(summary = "Editar serviço por Id", description = """
            Editar serviço por Id
            ---
            Editar serviço por Id no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando a serviço foi editado no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServicoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<ServicoResponseDto> editar(@PathVariable Integer id, @RequestBody ServicoRequestDto dto);

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar serviço", description = """
            Cancelar serviço
            ---
            Excluir serviço no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando a serviço é excluído com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    ))
    })
    ResponseEntity<Void> deletar(@PathVariable Integer id);

//    @GetMapping("/pendentes")
//    @Operation(summary = "Listar serviços pendentes", description = """
//            Lista todas as solicitações com status pendente
//            ---
//            Lista todas as solicitações com status pendente que estão cadastrados no banco de dados
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Quando existe solicitações com status pedente no banco de dados",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = ServicoResponseDto.class)
//                    )),
//            @ApiResponse(responseCode = "204", description = "Quando não existe nenhum serviço com status pendente no banco de dados")
//    })
//    ResponseEntity<List<ServicoResponseDto>> listarPendentes();
}
