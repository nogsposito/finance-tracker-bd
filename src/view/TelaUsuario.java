package view;

import dao.UsuarioDAO;
import model.Usuario;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

public class TelaUsuario extends JFrame {
    
    private JTextField telaNome;
    private JTextField telaSenha;

    public TelaUsuario() {

        setTitle("Tela de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(50, 20, 100, 30);
        add(labelNome);
        telaNome = new JTextField();
        telaNome.setBounds(50, 50, 200, 30);
        add(telaNome);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 80, 100, 30);
        add(labelSenha);
        telaSenha = new JTextField();
        telaSenha.setBounds(50, 100, 200, 30);
        add(telaSenha);

        JButton inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 200, 100, 30);
        add(inserirButton);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBounds(160, 200, 100, 30);
        add(deletarButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(270, 200, 100, 30);
        add(atualizarButton);

        inserirButton.addActionListener(e -> {
            try {

                Usuario novoUsuario = new Usuario(telaNome.getText(), telaSenha.getText());

                UsuarioDAO dao = new UsuarioDAO();
                dao.inserir(novoUsuario);

                JOptionPane.showMessageDialog(null, "Usuário inserido!");
                telaNome.setText("");
                telaSenha.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

        atualizarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("ID do usuário a atualizar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    String novoNome = JOptionPane.showInputDialog("Novo Nome:");
                    String novaSenha = JOptionPane.showInputDialog("Nova Senha:");

                    Usuario usuarioAtualizado = new Usuario(novoNome, novaSenha);
                    usuarioAtualizado.setId(Integer.parseInt(idStr)); // Seta o ID

                    UsuarioDAO dao = new UsuarioDAO();
                    dao.atualizar(usuarioAtualizado);

                    JOptionPane.showMessageDialog(null, "Usuário atualizado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Id do usuário para deletar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    int id = Integer.parseInt(idStr);

                    // Manda pro DAO
                    UsuarioDAO dao = new UsuarioDAO();
                    dao.deletar(id);

                    JOptionPane.showMessageDialog(null, "Usuário deletado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

    }

}
