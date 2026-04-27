package dao;

import model.Gasto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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