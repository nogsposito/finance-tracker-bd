import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
                Connection con = Conexao.conectar();
                String nome = telaNome.getText();
                String senha = telaSenha.getText();

                String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nome);
                ps.setString(2, senha);

                ps.executeUpdate();

                con.close();

                System.out.println("Usuário inserido!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        atualizarButton.addActionListener(e -> {
            try {
                Connection con = Conexao.conectar();

                String sql = "UPDATE Usuario SET nome = ?, senha = ? WHERE id_usuario = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                String id = javax.swing.JOptionPane.showInputDialog("ID do usuário:");
                String novo_nome = javax.swing.JOptionPane.showInputDialog("Novo Nome:");
                String nova_senha = javax.swing.JOptionPane.showInputDialog("Nova Senha:");

                ps.setString(1, novo_nome);
                ps.setString(2, nova_senha);
                ps.setInt(3, Integer.parseInt(id));

                ps.executeUpdate();

                con.close();

                System.out.println("Usuário atualizado!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                Connection con = Conexao.conectar();

                String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                String id = javax.swing.JOptionPane.showInputDialog("ID do usuário:");
                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                con.close();

                System.out.println("Usuário deletado!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

}
