import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaUsuario extends JFrame {
    
    private JTextField telaNome;
    private JTextField telaSenha;
    private JTextField telaData_cadastro;

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

        JLabel labelData_cadastro = new JLabel("Data de Cadastro:");
        labelData_cadastro.setBounds(50, 130, 100, 30);
        add(labelData_cadastro);
        telaData_cadastro = new JTextField();
        telaData_cadastro.setBounds(50, 150, 200, 30);
        add(telaData_cadastro);

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
                String dataCadastro = telaData_cadastro.getText();

                String sql = "INSERT INTO Usuario (nome, senha, data_cadastro) VALUES (?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nome);
                ps.setString(2, senha);
                ps.setString(3, dataCadastro);

                ps.executeUpdate();

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

    }

}
