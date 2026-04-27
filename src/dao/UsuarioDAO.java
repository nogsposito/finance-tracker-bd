package dao;

import model.Usuario;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

}