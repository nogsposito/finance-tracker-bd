package dao;

import model.Planejamento;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanejamentoDAO {

    public void inserir(Planejamento plan) {

        String sql = "INSERT INTO PlanejamentoFinanceiro (id_planejamento, nome, valor_limite, data_inicio, data_fim, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, plan.getIdPlanejamento());
            stmt.setString(2, plan.getNome());
            stmt.setDouble(3, plan.getValorLimite());
            stmt.setDate(4, new java.sql.Date(plan.getDataInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(plan.getDataFim().getTime()));
            stmt.setInt(6, plan.getIdUsuario());

            stmt.execute();
            System.out.println("Planejamento salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir planejamento: " + e.getMessage());
        }
    }

    public List<Planejamento> listar() {
        List<Planejamento> planejamentos = new ArrayList<>();
        String sql = "SELECT * FROM PlanejamentoFinanceiro";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Planejamento p = new Planejamento();
                p.setIdPlanejamento(rs.getInt("id_planejamento"));
                p.setNome(rs.getString("nome"));
                p.setValorLimite(rs.getDouble("valor_limite"));
                p.setDataInicio(rs.getDate("data_inicio"));
                p.setDataFim(rs.getDate("data_fim"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                planejamentos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar planejamentos: " + e.getMessage());
        }
        return planejamentos;
    }

    public void atualizar(Planejamento plan) {
        String sql = "UPDATE PlanejamentoFinanceiro SET nome = ?, valor_limite = ?, data_inicio = ?, data_fim = ?, id_usuario = ? WHERE id_planejamento = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plan.getNome());
            stmt.setDouble(2, plan.getValorLimite());
            stmt.setDate(3, new java.sql.Date(plan.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(plan.getDataFim().getTime()));
            stmt.setInt(5, plan.getIdUsuario());
            stmt.setInt(6, plan.getIdPlanejamento());

            stmt.execute();
            System.out.println("Planejamento atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar planejamento: " + e.getMessage());
        }
    }

    public void deletar(int idPlanejamento) {
        String sql = "DELETE FROM PlanejamentoFinanceiro WHERE id_planejamento = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPlanejamento);
            stmt.execute();
            System.out.println("Planejamento deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar planejamento: " + e.getMessage());
        }
    }

    public String analisarEstouro(int idPlanejamento) {

        String callSql = "CALL EncontrarEstouroPlanejamento(?)";
        String selectSql = "SELECT * FROM RegistroEstouro WHERE id_planejamento = ?";

        try (Connection conn = Conexao.conectar()) {

            try (CallableStatement cstmt = conn.prepareCall(callSql)) {
                cstmt.setInt(1, idPlanejamento);
                cstmt.execute();
            }

            try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                stmt.setInt(1, idPlanejamento);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int idGasto = rs.getInt("id_gasto_culpado");
                    double valorPassado = rs.getDouble("valor_passado");
                    return "ESTOURO DETECTADO! O gasto ID " + idGasto + " ultrapassou o limite em R$ " + valorPassado;
                } else {
                    return "Tudo certo! Limite não foi estourado.";
                }
            }

        } catch (SQLException e) {
            return "Erro ao analisar planejamento: " + e.getMessage();
        }
    }

}
