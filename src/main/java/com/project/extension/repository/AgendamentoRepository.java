package com.project.extension.repository;

import com.project.extension.dto.dashboard.ProximosAgendamentosResponseDto;
import com.project.extension.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query(
            value = """   
                SELECT COUNT(*)
                FROM agendamento a
                WHERE DATE(a.data_agendamento) >= CURRENT_DATE
            """,
                 nativeQuery = true
          )
    int countQtdAgendamentosHoje();

    @Query(
            value = """
                SELECT COUNT(*)
                FROM agendamento a
                WHERE DATE(a.data_agendamento) = CURRENT_DATE
                AND a.tipo = "SERVICO";
                """,
            nativeQuery = true
    )
    int countServicosHoje();

    @Query(
            value = """
                SELECT COUNT(*)
                FROM agendamento a
                WHERE DATE(a.data_agendamento) > CURRENT_TIMESTAMP
        """,
            nativeQuery = true
    )
    int countQtdAgendamentosFuturos();

    @Query("""
        SELECT new com.project.extension.dto.dashboard.ProximosAgendamentosResponseDto(
               a.id AS idAgendamento,
               a.dataAgendamento AS dataAgendamento,
               a.inicioAgendamento AS inicioAgendamento,
               a.fimAgendamento AS fimAgendamento,
               a.observacao AS agendamentoObservacao,
               p.valorTotal AS valorTotal,
               p.observacao AS pedidoObservacao,
               p.ativo AS ativo,
               s.nome AS status
        )
        FROM Agendamento a
        JOIN a.pedido p
        JOIN a.statusAgendamento s
        WHERE a.dataAgendamento = CURRENT_DATE
        ORDER BY a.inicioAgendamento ASC
    """)
    List<ProximosAgendamentosResponseDto> proximosAgendamentos();

    @Query(value = """
        SELECT
            CAST(
                (SUM(TIMESTAMPDIFF(MINUTE, a.inicio_agendamento, a.fim_agendamento)) / 60) /
                (COUNT(DISTINCT af.funcionario_id) * 9) * 100
            AS DECIMAL(5,2)) AS taxa_ocupacao_percentual
                FROM agendamento a
                JOIN agendamento_funcionario af ON a.id = af.agendamento_id
                WHERE a.data_agendamento = CURRENT_DATE
                GROUP BY a.data_agendamento;
        """,
    nativeQuery = true)

    Double taxaOcupacaoServicos();

}
