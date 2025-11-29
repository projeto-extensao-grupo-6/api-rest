package com.project.extension.repository;

import com.project.extension.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query("SELECT COUNT(*) FROM agendamento a WHERE DATE(a.data_agendamento) = CURRENT_TIMESTAMP")
    int countQtdAgendamentosHoje();

    @Query("SELECT COUNT(*) FROM agendamento a WHERE DATE(a.data_agendamento) > CURRENT_TIMESTAMP")
    int countQtdAgendamentosFuturos();

    @Query("""
            SELECT
                a.id AS agendamento_id,
                a.data_agendamento,
                a.observacao AS agendamento_observacao,
                p.id AS pedido_id,
                p.valor_total AS pedido_valor_total,
                p.observacao AS pedido_observacao,
                p.ativo AS pedido_ativo,
                e.id AS endereco_id,
                e.logradouro,
                e.numero,
                e.complemento,
                e.bairro,
                e.cidade,
                e.estado,
                e.uf,
                e.cep,
                e.referencia,
                e.tipo AS endereco_tipo,
                s.id AS status_id,
                s.nome AS status_nome
            FROM agendamento a
            JOIN pedido p ON a.pedido_id = p.id
            JOIN endereco e ON a.endereco_id = e.id
            JOIN status s ON a.status_id = s.id
            WHERE a.data_agendamento >= NOW()
            ORDER BY a.data_agendamento ASC;
            """)
    int proximosAgendamentos();
}
