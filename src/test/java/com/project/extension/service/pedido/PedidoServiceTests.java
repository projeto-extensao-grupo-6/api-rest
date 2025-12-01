// src/test/java/com/project/extension/service/pedido/PedidoServiceTests.java

package com.project.extension.service.pedido;

import com.project.extension.entity.*;
import com.project.extension.exception.naoencontrado.PedidoNaoEncontradoException;
import com.project.extension.repository.PedidoRepository;
import com.project.extension.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTests {

    @Mock private PedidoRepository repository;
    @Mock private StatusService statusService;
    @Mock private EtapaService etapaService;
    @Mock private ClienteService clienteService;
    @Mock private LogService logService;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("1 - Deve cadastrar pedido com sucesso quando Status, Etapa e Cliente já existem")
    void deveCadastrarPedidoComSucesso_EntidadesExistentes() {
        Pedido pedidoEntrada = criarPedidoBase();

        Status statusExistente = new Status(); statusExistente.setId(1);
        Etapa etapaExistente = new Etapa(); etapaExistente.setId(1);
        Cliente clienteExistente = new Cliente(); clienteExistente.setId(10);

        when(statusService.buscarPorTipoAndStatus("PEDIDO", "ATIVO")).thenReturn(statusExistente);
        when(etapaService.buscarPorTipoAndEtapa("PEDIDO", "PENDENTE")).thenReturn(etapaExistente);
        when(clienteService.buscarPorId(10)).thenReturn(clienteExistente);
        when(repository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(100);
            return p;
        });

        Pedido pedidoSalvo = pedidoService.cadastrar(pedidoEntrada);

        assertNotNull(pedidoSalvo.getId());
        assertEquals(100, pedidoSalvo.getId());
        assertEquals(statusExistente, pedidoSalvo.getStatus());
        assertEquals(etapaExistente, pedidoSalvo.getEtapa());
        assertEquals(clienteExistente, pedidoSalvo.getCliente());

        verify(statusService, never()).cadastrar(any());
        verify(etapaService, never()).cadastrar(any());
        verify(clienteService, never()).cadastrar(any());
        verify(logService).success(anyString());
    }

    @Test
    @DisplayName("2 - Deve criar Status e Etapa automaticamente quando não existirem")
    void deveCriarStatusEEtapaAutomaticamente() {
        Pedido pedido = criarPedidoBase();

        when(statusService.buscarPorTipoAndStatus("PEDIDO", "ATIVO")).thenReturn(null);
        when(etapaService.buscarPorTipoAndEtapa("PEDIDO", "PENDENTE")).thenReturn(null);
        when(clienteService.buscarPorId(10)).thenReturn(new Cliente());

        Status statusCriado = new Status(); statusCriado.setId(5);
        Etapa etapaCriada = new Etapa(); etapaCriada.setId(7);

        when(statusService.cadastrar(any(Status.class))).thenReturn(statusCriado);
        when(etapaService.cadastrar(any(Etapa.class))).thenReturn(etapaCriada);
        when(repository.save(any(Pedido.class))).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            p.setId(200);
            return p;
        });

        Pedido salvo = pedidoService.cadastrar(pedido);

        assertEquals(200, salvo.getId());
        verify(statusService).cadastrar(any(Status.class));
        verify(etapaService).cadastrar(any(Etapa.class));
        verify(logService, times(2)).info(anyString());
        verify(logService).success(anyString());
    }
    @Test
    @DisplayName("3 - Deve cadastrar cliente automaticamente quando não existir")
    void deveCadastrarClienteAutomaticamente() {
        Pedido pedido = criarPedidoBase();
        Cliente clienteNovo = new Cliente();
        clienteNovo.setId(99);
        clienteNovo.setNome("Maria Oliveira");

        when(statusService.buscarPorTipoAndStatus(any(), any())).thenReturn(new Status());
        when(etapaService.buscarPorTipoAndEtapa(any(), any())).thenReturn(new Etapa());
        when(clienteService.buscarPorId(10)).thenReturn(null);
        when(clienteService.cadastrar(any(Cliente.class))).thenReturn(clienteNovo);
        when(repository.save(any(Pedido.class))).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            p.setId(300);
            return p;
        });

        Pedido salvo = pedidoService.cadastrar(pedido);

        assertEquals(300, salvo.getId());
        assertEquals(clienteNovo, salvo.getCliente());

        verify(clienteService).cadastrar(any(Cliente.class));
        verify(repository).save(any(Pedido.class));
    }

        @Test
    @DisplayName("4 - Deve lançar exceção ao buscar pedido inexistente")
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        int idInexistente = 999;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscarPorId(idInexistente));

        verify(logService).error(anyString());
        verify(logService).error(contains("Pedido com ID 999 não encontrado"));
    }

    @Test
    @DisplayName("5 - Deve editar pedido atualizando campos corretamente")
    void deveEditarPedidoComSucesso() {
        Pedido existente = new Pedido();
        existente.setId(50);
        existente.setValorTotal(BigDecimal.valueOf(1000));
        existente.setAtivo(true);
        existente.setObservacao("Antiga");
        existente.setStatus(new Status());
        existente.setEtapa(new Etapa());
        existente.setCliente(new Cliente());

        Pedido atualizacao = new Pedido();
        atualizacao.setValorTotal(BigDecimal.valueOf(3500.90));
        atualizacao.setAtivo(false);
        atualizacao.setObservacao("Atualizada");

        Status statusNovo = new Status(); statusNovo.setId(10);
        Etapa etapaNova = new Etapa(); etapaNova.setId(20);

        when(repository.findById(50)).thenReturn(Optional.of(existente));
        when(statusService.buscarPorTipoAndStatus(any(), any())).thenReturn(statusNovo);
        when(etapaService.buscarPorTipoAndEtapa(any(), any())).thenReturn(etapaNova);
        when(repository.save(any(Pedido.class))).thenReturn(existente);

        Pedido editado = pedidoService.editar(atualizacao, 50);

        assertEquals(BigDecimal.valueOf(3500.90), editado.getValorTotal());
        assertFalse(editado.getAtivo());
        assertEquals("Atualizada", editado.getObservacao());
        assertEquals(statusNovo, editado.getStatus());
        assertEquals(etapaNova, editado.getEtapa());

        verify(logService).info(contains("atualizado com sucesso"));
    }

    // === Método auxiliar para criar pedido padrão nos testes ===
    private Pedido criarPedidoBase() {
        Pedido p = new Pedido();
        p.setValorTotal(BigDecimal.valueOf(1500.00));
        p.setAtivo(true);
        p.setObservacao("Orçamento pintura");

        Status s = new Status();
        s.setTipo("PEDIDO");
        s.setNome("ATIVO");
        p.setStatus(s);

        Etapa e = new Etapa();
        e.setTipo("PEDIDO");
        e.setNome("PENDENTE");
        p.setEtapa(e);

        Cliente c = new Cliente();
        c.setId(10);
        p.setCliente(c);

        return p;
    }
}