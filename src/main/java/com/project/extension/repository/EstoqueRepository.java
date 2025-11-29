package com.project.extension.repository;

import com.project.extension.entity.Estoque;
import com.project.extension.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByProdutoAndLocalizacao(Produto produto, String localizacao);

    Optional<Estoque> findByProduto(Produto produto);

    @Query("""
        SELECT COUNT(*)
        FROM Estoque e
        JOIN e.produto p
        JOIN p.metricaEstoque m
        WHERE e.quantidadeDisponivel < m.nivelMinimo
    """)
    int countItensAbaixoMinimo();
}
