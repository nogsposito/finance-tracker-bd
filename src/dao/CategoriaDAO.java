package dao;

import model.Categoria;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO Categoria (id_categoria, nome, descricao, id_categoria_pai) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoria.getIdCategoria());
            stmt.setString(2, categoria.getNome());
            stmt.setString(3, categoria.getDescricao());

            if (categoria.getIdCategoriaPai() != null) {
                stmt.setInt(4, categoria.getIdCategoriaPai());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.execute();
            System.out.println("Categoria salva com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria: " + e.getMessage());
            throw new RuntimeException("Erro no banco: " + e.getMessage());
        }
    }

    public List<Categoria> listar() {

        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";

        try (Connection conn = Conexao.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                int idPai = rs.getInt("id_categoria_pai");
                if (!rs.wasNull()) {
                    c.setIdCategoriaPai(idPai);
                }
                categorias.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }

        return categorias;

    }

    public void atualizar(Categoria categoria) {

        String sql = "UPDATE Categoria SET nome = ?, descricao = ? WHERE id_categoria = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            if (categoria.getIdCategoriaPai() != null) {
                stmt.setInt(3, categoria.getIdCategoriaPai());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setInt(3, categoria.getIdCategoria());

            stmt.execute();
            System.out.println("Categoria atualizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    public void deletar(int idCategoria) {
        String sql = "DELETE FROM Categoria WHERE id_categoria = ?";
        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar: " + e.getMessage());
        }
    }

}
