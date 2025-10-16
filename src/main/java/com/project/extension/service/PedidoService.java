package com.project.extension.service;

import com.project.extension.entity.Pedido;
import com.project.extension.entity.Status;
import com.project.extension.exception.naoencontrado.PedidoNaoEncontradoException;
import com.project.extension.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final StatusService statusService;

    public Pedido cadastrar(Pedido pedido) {

        Status statusSalvo = statusService.buscarPorTipoAndStatus(
                pedido.getStatus().getTipo(),
                pedido.getStatus().getNome()
        );

        if (statusSalvo == null) {
            statusSalvo = statusService.cadastrar(pedido.getStatus());
            log.info("Status criado: {} - {}", statusSalvo.getTipo(), statusSalvo.getNome());
        }

        pedido.setStatus(statusSalvo);

        Pedido pedidoSalvo = repository.save(pedido);
        log.info("Pedido salvo com sucesso!");

        return pedidoSalvo;
    }


    public Pedido buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("Pedido com ID " + id + " não encontrado");
            return new PedidoNaoEncontradoException();
        });
    }

    public List<Pedido> listar() {
        List<Pedido> pedidos = repository.findAll();
        log.info("Total de pedidos encontrados: " + pedidos.size());
        return pedidos;
    }

    private void atualizarCampos(Pedido destino, Pedido origem) {
       destino.setValorTotal(origem.getValorTotal());
       destino.setAtivo(origem.getAtivo());
       destino.setObservacao(origem.getObservacao());

        if (origem.getStatus() != null) {
            Status statusAtualizado = statusService.buscarPorTipoAndStatus(origem.getStatus().getTipo(),
                    origem.getStatus().getNome());
            destino.setStatus(statusAtualizado);
        }
    }

    public Pedido editar(Pedido origem, Integer id) {
        Pedido destino = this.buscarPorId(id);
        this.atualizarCampos(destino, origem);
        Pedido pedidoAtualizado = this.cadastrar(destino);
        log.info("Pedido atualizado com sucesso!");
        return pedidoAtualizado;
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
        log.info("Pedido deletado com sucesso");
    }
}
