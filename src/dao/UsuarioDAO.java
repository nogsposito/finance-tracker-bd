package dao;

import model.Usuario;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";

        // O try-with-resources já fecha a conexão automaticamente
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            ps.executeUpdate();
            System.out.println("Usuário inserido com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir usuário", ex);
        }
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNome(rs.getString("nome"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return usuarios;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nome = ?, senha = ? WHERE id_usuario = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();
            System.out.println("Usuário atualizado com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar usuário", ex);
        }
    }

    public void deletar(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            System.out.println("Usuário deletado com sucesso pelo DAO!");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar usuário", ex);
        }
    }

    public double obterTotalGastoUsuario(int idUsuario) {
        // Usa o SELECT para retornar o valor da sua função
        String sql = "SELECT fn_total_gasto_usuario(?) AS total";
        double total = 0;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao chamar função: " + e.getMessage());
        }
        return total;
    }

}