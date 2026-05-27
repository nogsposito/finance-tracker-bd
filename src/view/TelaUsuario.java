package view;

import dao.UsuarioDAO;
import model.Usuario;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaUsuario extends JFrame {
    
    private JTextField telaNome;
    private JTextField telaSenha;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollPane;

    public TelaUsuario() {

        setTitle("Tela de Usuário");
        setSize(610, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(50, 20, 100, 30);
        add(labelNome);
        telaNome = new JTextField();
        telaNome.setBounds(50, 50, 500, 30);
        add(telaNome);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 80, 100, 30);
        add(labelSenha);
        telaSenha = new JTextField();
        telaSenha.setBounds(50, 100, 500, 30);
        add(telaSenha);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Senha"}, 0);
        tabela = new JTable(modeloTabela);
        scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(50, 200, 500, 200); // Tabela fica abaixo dos botões
        add(scrollPane);
        carregarTabela();

        JButton inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 140, 100, 30);
        add(inserirButton);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBounds(160, 140, 100, 30);
        add(deletarButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(270, 140, 100, 30);
        add(atualizarButton);

        JButton verTotalButton = new JButton("Ver Total Gasto (Função)");
        verTotalButton.setBounds(380, 140, 180, 30);
        add(verTotalButton);

        verTotalButton.addActionListener(e -> {
            try {
                // Pede o ID do usuário através de um pop-up
                String input = JOptionPane.showInputDialog(this, "Digite o ID do Usuário para ver o total gasto:");
                if (input != null && !input.isEmpty()) {
                    int idSelecionado = Integer.parseInt(input);
                    UsuarioDAO dao = new UsuarioDAO();
                    double total = dao.obterTotalGastoUsuario(idSelecionado);
                    JOptionPane.showMessageDialog(this, "O total gasto pelo usuário " + idSelecionado + " é: R$ " + total, "Resultado da Função", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um ID numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

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

    private void carregarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela antes de carregar
        try {
            UsuarioDAO dao = new UsuarioDAO();
            // Presumindo que seu UsuarioDAO tenha um método listar()
            for (Usuario u : dao.listar()) {
                modeloTabela.addRow(new Object[]{u.getId(), u.getNome(), u.getSenha()});
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

}
