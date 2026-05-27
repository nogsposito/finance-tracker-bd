package view;

import dao.CategoriaDAO;
import model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaCategoria extends JFrame {

    // Campos de texto para a inserção
    private JTextField telaNome;
    private JTextField telaDescricao;
    private JTextField telaIdPai;

    // Botões
    private JButton inserirButton;
    private JButton atualizarButton;
    private JButton deletarButton;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollPane;

    public TelaCategoria() {
        setTitle("Tela de Categoria");
        setSize(610, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Mantendo o layout absoluto como na TelaUsuario

        // 1. Campo Nome
        JLabel labelNome = new JLabel("Nome da Categoria:");
        labelNome.setBounds(50, 20, 200, 30);
        add(labelNome);

        telaNome = new JTextField();
        telaNome.setBounds(50, 50, 500, 30);
        add(telaNome);

        // 2. Campo Descrição
        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 80, 200, 30);
        add(labelDescricao);

        telaDescricao = new JTextField();
        telaDescricao.setBounds(50, 110, 500, 30);
        add(telaDescricao);

        // 3. Campo ID Categoria Pai
        JLabel labelIdPai = new JLabel("ID Categoria Pai (Deixe em branco se for principal):");
        labelIdPai.setBounds(50, 140, 350, 30);
        add(labelIdPai);

        telaIdPai = new JTextField();
        telaIdPai.setBounds(50, 170, 500, 30);
        add(telaIdPai);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "ID Pai"}, 0);
        tabela = new JTable(modeloTabela);
        scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(50, 280, 500, 200);
        add(scrollPane);

        carregarTabela();

        // Inicialização dos Botões
        inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 230, 100, 30);
        add(inserirButton);

        atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(160, 230, 100, 30);
        add(atualizarButton);

        deletarButton = new JButton("Deletar");
        deletarButton.setBounds(270, 230, 100, 30);
        add(deletarButton);


        // --- AÇÕES DOS BOTÕES ---

        inserirButton.addActionListener(e -> {
            try {
                Categoria novaCategoria = new Categoria();
                novaCategoria.setNome(telaNome.getText());
                novaCategoria.setDescricao(telaDescricao.getText());

                // Trata o campo opcional de Categoria Pai
                String idPaiText = telaIdPai.getText();
                if (idPaiText != null && !idPaiText.trim().isEmpty()) {
                    novaCategoria.setIdCategoriaPai(Integer.parseInt(idPaiText.trim()));
                } else {
                    novaCategoria.setIdCategoriaPai(null);
                }

                CategoriaDAO dao = new CategoriaDAO();
                dao.inserir(novaCategoria);

                JOptionPane.showMessageDialog(null, "Categoria inserida!");

                // Limpa os campos após inserir
                telaNome.setText("");
                telaDescricao.setText("");
                telaIdPai.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir: " + ex.getMessage());
            }
        });

        atualizarButton.addActionListener(e -> {
            try {
                // Pede o ID e os novos dados através de pop-ups (mesma lógica do seu exemplo)
                String idStr = JOptionPane.showInputDialog("ID da categoria a atualizar:");

                if (idStr != null && !idStr.trim().isEmpty()) {
                    String novoNome = JOptionPane.showInputDialog("Novo Nome da Categoria:");
                    String novaDescricao = JOptionPane.showInputDialog("Nova Descrição:");
                    String novoIdPai = JOptionPane.showInputDialog("Novo ID Categoria Pai (Cancele ou deixe em branco se não houver):");

                    Categoria categoriaAtualizada = new Categoria();
                    categoriaAtualizada.setIdCategoria(Integer.parseInt(idStr)); // Seta o ID procurado
                    categoriaAtualizada.setNome(novoNome);
                    categoriaAtualizada.setDescricao(novaDescricao);

                    if (novoIdPai != null && !novoIdPai.trim().isEmpty()) {
                        categoriaAtualizada.setIdCategoriaPai(Integer.parseInt(novoIdPai.trim()));
                    } else {
                        categoriaAtualizada.setIdCategoriaPai(null);
                    }

                    CategoriaDAO dao = new CategoriaDAO();
                    dao.atualizar(categoriaAtualizada);

                    JOptionPane.showMessageDialog(null, "Categoria atualizada!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Id da categoria para deletar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    int id = Integer.parseInt(idStr);

                    // Manda pro DAO
                    CategoriaDAO dao = new CategoriaDAO();
                    dao.deletar(id);

                    JOptionPane.showMessageDialog(null, "Categoria deletada!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            CategoriaDAO dao = new CategoriaDAO();
            for (Categoria c : dao.listar()) {
                modeloTabela.addRow(new Object[]{
                        c.getIdCategoria(), c.getNome(), c.getDescricao(), c.getIdCategoriaPai()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

}