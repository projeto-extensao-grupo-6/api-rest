package com.project.extension.service;

import com.project.extension.entity.Estoque;
import com.project.extension.entity.Produto;
import com.project.extension.exception.naoencontrado.EstoqueNaoEncontradoException;
import com.project.extension.repository.EstoqueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoService produtoService;

    public Estoque entrada(Estoque request) {
        Produto produto = produtoService.buscarPorId(request.getProduto().getId());

        Estoque estoqueExistente = repository.findByProdutoAndLocalizacao(produto, request.getLocalizacao())
                .orElseGet(() -> {
                    Estoque novo = new Estoque();
                    novo.setProduto(produto);
                    novo.setLocalizacao(request.getLocalizacao());
                    novo.setReservado(0);
                    novo.setQuantidade(0);
                    return novo;
                });

        int quantidadeAtual = estoqueExistente.getQuantidade() == null ? 0 : estoqueExistente.getQuantidade();
        int novaQuantidade = quantidadeAtual + (request.getQuantidade() == null ? 0 : request.getQuantidade());
        estoqueExistente.setQuantidade(novaQuantidade);

        Estoque salvo = repository.save(estoqueExistente);
        log.info("Entrada registrada: {} unidades de '{}' em '{}'", request.getQuantidade(), produto.getNome(), request.getLocalizacao());
        return salvo;
    }

    public Estoque saida(Estoque request) {
        Produto produto = produtoService.buscarPorId(request.getProduto().getId());

        Estoque estoqueExistente = repository.findByProdutoAndLocalizacao(produto, request.getLocalizacao())
                .orElseThrow(EstoqueNaoEncontradoException::new);

        int quantidadeAtual = estoqueExistente.getQuantidade() == null ? 0 : estoqueExistente.getQuantidade();
        int quantidadeSaida = request.getQuantidade() == null ? 0 : request.getQuantidade();

        int novaQuantidade = Math.max(quantidadeAtual - quantidadeSaida, 0);
        estoqueExistente.setQuantidade(novaQuantidade);

        Estoque salvo = repository.save(estoqueExistente);
        log.info("Saída registrada: {} unidades de '{}' em '{}'. Quantidade final: {}",
                quantidadeSaida, produto.getNome(), request.getLocalizacao(), novaQuantidade);

        return salvo;
    }

    public List<Estoque> listar() {
        List<Estoque> estoques = repository.findAll();
        log.info("Total de registros de estoque encontrados: {}", estoques.size());
        return estoques;
    }

    public Estoque buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Estoque com ID {} não encontrado", id);
                    return new EstoqueNaoEncontradoException();
                });
    }
}