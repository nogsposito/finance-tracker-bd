package dao;

import model.Gasto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GastoDAO {

    public void inserir(Gasto gasto) {
        String sql = "INSERT INTO Gasto (valor, descricao, data, id_usuario, id_planejamento, id_estabelecimento, id_categoria, id_forma_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, gasto.getValor());
            ps.setString(2, gasto.getDescricao());
            ps.setDate(3, gasto.getData());
            ps.setInt(4, gasto.getIdUsuario());
            ps.setInt(5, gasto.getIdPlanejamento());
            ps.setInt(6, gasto.getIdEstabelecimento());
            ps.setInt(7, gasto.getIdCategoria());
            ps.setInt(8, gasto.getIdFormaPagamento());

            ps.executeUpdate();
            System.out.println("Gasto inserido com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir gasto: " + ex.getMessage(), ex);
        }
    }

    public List<Gasto> listar() {
        List<Gasto> gastos = new ArrayList<>();
        String sql = "SELECT * FROM Gasto";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Gasto g = new Gasto();
                g.setId(rs.getInt("id_gasto"));
                g.setValor(rs.getDouble("valor"));
                g.setDescricao(rs.getString("descricao"));
                g.setData(rs.getDate("data"));
                g.setIdUsuario(rs.getInt("id_usuario"));

                // Tratamento especial para id_planejamento pois ele permite null no banco
                int idPlan = rs.getInt("id_planejamento");
                if (!rs.wasNull()) {
                    g.setIdPlanejamento(idPlan);
                }

                g.setIdEstabelecimento(rs.getInt("id_estabelecimento"));
                g.setIdCategoria(rs.getInt("id_categoria"));
                g.setIdFormaPagamento(rs.getInt("id_forma_pagamento"));

                gastos.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar gastos: " + e.getMessage());
        }
        return gastos;
    }

    public void atualizar(Gasto gasto) {
        String sql = "UPDATE Gasto SET valor = ?, descricao = ?, data = ?, id_usuario = ?, id_planejamento = ?, id_estabelecimento = ?, id_categoria = ?, id_forma_pagamento = ? WHERE id_gasto = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, gasto.getValor());
            ps.setString(2, gasto.getDescricao());
            ps.setDate(3, gasto.getData());
            ps.setInt(4, gasto.getIdUsuario());
            ps.setInt(5, gasto.getIdPlanejamento());
            ps.setInt(6, gasto.getIdEstabelecimento());
            ps.setInt(7, gasto.getIdCategoria());
            ps.setInt(8, gasto.getIdFormaPagamento());
            ps.setInt(9, gasto.getId());

            ps.executeUpdate();
            System.out.println("Gasto atualizado com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar gasto", ex);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Gasto WHERE id_gasto = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Gasto deletado com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar gasto", ex);
        }
    }
}