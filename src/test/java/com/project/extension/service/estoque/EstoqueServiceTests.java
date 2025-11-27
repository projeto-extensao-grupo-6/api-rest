package com.project.extension.service.estoque;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.extension.entity.Estoque;
import com.project.extension.entity.HistoricoEstoque;
import com.project.extension.entity.Produto;
import com.project.extension.entity.Usuario;
import com.project.extension.exception.naopodesernegativo.EstoqueNaoPodeSerNegativoException;
import com.project.extension.repository.EstoqueRepository;
import com.project.extension.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public class EstoqueServiceTests {

    @Mock
    private EstoqueRepository repository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private HistoricoEstoqueService historicoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LogService logService;

    @InjectMocks
    private EstoqueService service;

    private Produto produto;
    private Estoque estoque;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");

        estoque = new Estoque();
        estoque.setId(1);
        estoque.setProduto(produto);
        estoque.setLocalizacao("Depósito A");
        estoque.setQuantidadeTotal(10);
        estoque.setQuantidadeDisponivel(10);
        estoque.setReservado(0);

        usuario = new Usuario();
        usuario.setId(99);
        usuario.setNome("Usuário Teste");

        when(usuarioService.buscarPorId(99)).thenReturn(usuario);
    }

    @Test
    void testEntradaComSucesso() {
        Estoque request = new Estoque();
        request.setProduto(produto);
        request.setLocalizacao("Depósito A");
        request.setQuantidadeTotal(5);

        when(produtoService.buscarPorId(produto.getId())).thenReturn(produto);
        when(repository.findByProdutoAndLocalizacao(produto, "Depósito A")).thenReturn(Optional.of(estoque));
        when(repository.save(any(Estoque.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estoque resultado = service.entrada(request);

        assertEquals(15, resultado.getQuantidadeTotal());
        assertEquals(15, resultado.getQuantidadeDisponivel());
        verify(historicoService).cadastrar(any(HistoricoEstoque.class));
        verify(logService).info(contains("Movimentação de estoque (Tipo: ENTRADA)"));
    }

    @Test
    void testSaidaComSucesso() {

        Estoque request = new Estoque();
        request.setProduto(produto);
        request.setLocalizacao("Depósito A");
        request.setQuantidadeTotal(5);

        when(produtoService.buscarPorId(produto.getId())).thenReturn(produto);
        when(repository.findByProdutoAndLocalizacao(produto, "Depósito A")).thenReturn(Optional.of(estoque));
        when(repository.save(any(Estoque.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estoque resultado = service.saida(request);

        assertEquals(5, resultado.getQuantidadeTotal());
        assertEquals(5, resultado.getQuantidadeDisponivel());
        verify(historicoService).cadastrar(any(HistoricoEstoque.class));
        verify(logService).info(contains("Movimentação de estoque (Tipo: SAIDA)"));
    }

    @Test
    void testSaidaComEstoqueInsuficiente() {
        Estoque request = new Estoque();
        request.setProduto(produto);
        request.setLocalizacao("Depósito A");
        request.setQuantidadeTotal(20); // maior que disponível

        when(produtoService.buscarPorId(produto.getId())).thenReturn(produto);
        when(repository.findByProdutoAndLocalizacao(produto, "Depósito A")).thenReturn(Optional.of(estoque));

        assertThrows(EstoqueNaoPodeSerNegativoException.class, () -> service.saida(request));
        verify(logService).warning(contains("Estoque insuficiente"));
    }

    @Test
    void testMovimentacaoComQuantidadeZero() {
        Estoque request = new Estoque();
        request.setProduto(produto);
        request.setLocalizacao("Depósito A");
        request.setQuantidadeTotal(0);

        when(produtoService.buscarPorId(produto.getId())).thenReturn(produto);
        when(repository.findByProdutoAndLocalizacao(produto, "Depósito A")).thenReturn(Optional.of(estoque));

        assertThrows(IllegalArgumentException.class, () -> service.entrada(request));
        verify(logService).error(contains("quantidade menor ou igual a zero"));
    }

    @Test
    void testListarEstoques() {
        when(repository.findAll()).thenReturn(List.of(estoque));

        List<Estoque> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals(estoque, resultado.get(0));
        verify(logService).info(contains("Busca por todos os registros de estoque realizada"));
    }
}
