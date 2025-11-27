package com.project.extension.controller.estoque;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.extension.controller.estoque.EstoqueControllerImpl;
import com.project.extension.dto.atributo.AtributoProdutoResponseDto;
import com.project.extension.dto.estoque.EstoqueMapper;
import com.project.extension.dto.estoque.EstoqueRequestDto;
import com.project.extension.dto.estoque.EstoqueResponseDto;
import com.project.extension.dto.metrica.MetricaEstoqueResponseDto;
import com.project.extension.dto.produto.ProdutoResponseDto;
import com.project.extension.entity.Estoque;
import com.project.extension.entity.Produto;
import com.project.extension.exception.naopodesernegativo.EstoqueNaoPodeSerNegativoException;
import com.project.extension.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Collections;

public class EstoqueControllerTests {

    @Mock
    private EstoqueService service;

    @Mock
    private EstoqueMapper mapper;

    @InjectMocks
    private EstoqueControllerImpl controller;

    private Estoque estoque;
    private EstoqueRequestDto requestDto;
    private EstoqueResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        estoque = new Estoque();
        estoque.setId(1);
        estoque.setLocalizacao("Depósito A");
        estoque.setQuantidadeTotal(10);

        requestDto = new EstoqueRequestDto(1, "Depósito A", 5);

        MetricaEstoqueResponseDto metrica = new MetricaEstoqueResponseDto(1, 1, 10);
        AtributoProdutoResponseDto atributo = new AtributoProdutoResponseDto(1, "Ativo", "10");
        ProdutoResponseDto produto = new ProdutoResponseDto(1, "Vidro", "Vidro Temperado", "centimetros"
        , 100.00, true, metrica, List.of(atributo));

        responseDto = new EstoqueResponseDto(1, 20, 10,
                5, "Depósito A", produto);
    }

    @Test
    void testEntrada() {
        when(mapper.toEntity(requestDto)).thenReturn(estoque);
        when(service.entrada(estoque)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        ResponseEntity<EstoqueResponseDto> response = controller.entrada(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(service).entrada(estoque);
        verify(mapper).toEntity(requestDto);
        verify(mapper).toResponse(estoque);
    }

    @Test
    void testEntradaIncorreta() {
        when(mapper.toEntity(requestDto)).thenReturn(estoque);
        when(service.entrada(estoque)).thenThrow(new IllegalArgumentException("Quantidade movimentada deve ser maior que zero"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.entrada(requestDto));

        assertEquals("Quantidade movimentada deve ser maior que zero", exception.getMessage());

        verify(service).entrada(estoque);
        verify(mapper).toEntity(requestDto);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void testSaida() {
        when(mapper.toEntity(requestDto)).thenReturn(estoque);
        when(service.saida(estoque)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        ResponseEntity<EstoqueResponseDto> response = controller.saida(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(service).saida(estoque);
        verify(mapper).toEntity(requestDto);
        verify(mapper).toResponse(estoque);
    }

    @Test
    void testSaidaIncorreta() {
        when(mapper.toEntity(requestDto)).thenReturn(estoque);
        when(service.saida(estoque)).thenThrow(new EstoqueNaoPodeSerNegativoException("Estoque insuficiente"));

        EstoqueNaoPodeSerNegativoException exception = assertThrows(
                EstoqueNaoPodeSerNegativoException.class,
                () -> controller.saida(requestDto)
        );

        assertEquals("Estoque insuficiente", exception.getMessage());

        verify(service).saida(estoque);
        verify(mapper).toEntity(requestDto);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void testBuscarPorId() {
        when(service.buscarPorId(1)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        ResponseEntity<EstoqueResponseDto> response = controller.buscarPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(service).buscarPorId(1);
        verify(mapper).toResponse(estoque);
    }

    @Test
    void testBuscarPorIdIncorreto() {
        int idIncorreto = 999;
        when(service.buscarPorId(idIncorreto))
                .thenThrow(new RuntimeException("Estoque não encontrado"));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> controller.buscarPorId(idIncorreto));

        assertEquals("Estoque não encontrado", exception.getMessage());
        verify(service).buscarPorId(idIncorreto);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void testListarComResultados() {
        when(service.listar()).thenReturn(List.of(estoque));
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        ResponseEntity<List<EstoqueResponseDto>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDto, response.getBody().get(0));
        verify(service).listar();
        verify(mapper).toResponse(estoque);
    }

    @Test
    void testListarSemResultados() {
        when(service.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<List<EstoqueResponseDto>> response = controller.listar();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(service).listar();
        verify(mapper, never()).toResponse(any());
    }
}
