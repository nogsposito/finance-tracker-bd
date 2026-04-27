package view;

import dao.GastoDAO;
import model.Gasto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

public class TelaGasto extends JFrame {

    // Gasto(id_gasto, valor, descricao, data, id_usuario, id_planejamento_financeiro, id_estabelecimento, id_categoria, id_forma_pagamento)

    private JTextField valor;
    private JTextField descricao;
    private JTextField data;
    private JTextField usuarioId;
    private JTextField planejamentoId;
    private JTextField estabelecimentoId;
    private JTextField categoriaId;
    private JTextField formaPagamentoId;


    public TelaGasto() {
        setTitle("Tela de Gasto");
        setSize(400, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelValor = new JLabel("Valor:");
        labelValor.setBounds(50, 20, 100, 30);
        add(labelValor);
        valor = new JTextField();
        valor.setBounds(50, 50, 200, 30);
        add(valor);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 80, 100, 30);
        add(labelDescricao);
        descricao = new JTextField();
        descricao.setBounds(50, 100, 200, 30);
        add(descricao);

        JLabel labelData = new JLabel("Data:");
        labelData.setBounds(50, 130, 100, 30);
        add(labelData);
        data = new JTextField();
        data.setBounds(50, 150, 200, 30);
        add(data);

        JLabel labelUsuarioId = new JLabel("ID do Usuário:");
        labelUsuarioId.setBounds(50, 180, 100, 30);
        add(labelUsuarioId);
        usuarioId = new JTextField();
        usuarioId.setBounds(50, 200, 200, 30);
        add(usuarioId);

        JLabel labelPlanejamentoId = new JLabel("ID do Planejamento:");
        labelPlanejamentoId.setBounds(50, 230, 100, 30);
        add(labelPlanejamentoId);
        planejamentoId = new JTextField();
        planejamentoId.setBounds(50, 250, 200, 30);
        add(planejamentoId);

        JLabel labelEstabelecimentoId = new JLabel("ID do Estabelecimento:");
        labelEstabelecimentoId.setBounds(50, 280, 100, 30);
        add(labelEstabelecimentoId);
        estabelecimentoId = new JTextField();
        estabelecimentoId.setBounds(50, 300, 200, 30);
        add(estabelecimentoId);

        JLabel labelCategoriaId = new JLabel("ID da Categoria:");
        labelCategoriaId.setBounds(50, 330, 100, 30);
        add(labelCategoriaId);
        categoriaId = new JTextField();
        categoriaId.setBounds(50, 350, 200, 30);
        add(categoriaId);

        JLabel labelFormaPagamentoId = new JLabel("ID da Forma de Pagamento:");
        labelFormaPagamentoId.setBounds(50, 380, 100, 30);
        add(labelFormaPagamentoId);
        formaPagamentoId = new JTextField();
        formaPagamentoId.setBounds(50, 400, 200, 30);
        add(formaPagamentoId);

        JButton inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 500, 100, 30);
        add(inserirButton);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBounds(160, 500, 100, 30);
        add(deletarButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(270, 500, 100, 30);
        add(atualizarButton);

        inserirButton.addActionListener(e -> {
            try {
                // Monta o objeto Gasto lendo os campos da tela
                Gasto novoGasto = new Gasto(
                        Double.parseDouble(valor.getText()),
                        descricao.getText(),
                        java.sql.Date.valueOf(data.getText()), // Data no formato AAAA-MM-DD
                        Integer.parseInt(usuarioId.getText()),
                        Integer.parseInt(planejamentoId.getText()),
                        Integer.parseInt(estabelecimentoId.getText()),
                        Integer.parseInt(categoriaId.getText()),
                        Integer.parseInt(formaPagamentoId.getText())
                );

                // Envia para o DAO salvar
                GastoDAO dao = new GastoDAO();
                dao.inserir(novoGasto);

                JOptionPane.showMessageDialog(this, "Gasto inserido com sucesso!");
                valor.setText("");
                descricao.setText("");
                data.setText("");
                usuarioId.setText("");
                planejamentoId.setText("");
                estabelecimentoId.setText("");
                categoriaId.setText("");
                formaPagamentoId.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique se todos os IDs numéricos e a Data (AAAA-MM-DD) estão preenchidos corretamente!\n" + ex.getMessage());
            }
        });

        atualizarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("ID do gasto a atualizar:");
                if (idStr != null && !idStr.trim().isEmpty()) {

                    // Lendo os novos dados pelos pop-ups e convertendo
                    double v = Double.parseDouble(JOptionPane.showInputDialog("Novo valor:"));
                    String d = JOptionPane.showInputDialog("Nova descricao:");
                    java.sql.Date dt = java.sql.Date.valueOf(JOptionPane.showInputDialog("Nova data (AAAA-MM-DD):"));
                    int uId = Integer.parseInt(JOptionPane.showInputDialog("Novo ID usuario:"));
                    int pId = Integer.parseInt(JOptionPane.showInputDialog("Novo ID planejamento:"));
                    int eId = Integer.parseInt(JOptionPane.showInputDialog("Novo ID estabelecimento:"));
                    int cId = Integer.parseInt(JOptionPane.showInputDialog("Novo ID categoria:"));
                    int fId = Integer.parseInt(JOptionPane.showInputDialog("Novo ID forma_pagamento:"));

                    // Cria o Gasto
                    Gasto gastoAtualizado = new Gasto(v, d, dt, uId, pId, eId, cId, fId);
                    gastoAtualizado.setId(Integer.parseInt(idStr)); // Seta o ID a ser atualizado

                    // Executa o DAO
                    GastoDAO dao = new GastoDAO();
                    dao.atualizar(gastoAtualizado);

                    JOptionPane.showMessageDialog(this, "Gasto atualizado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar. Preencha os campos corretamente: " + ex.getMessage());
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("ID do gasto para deletar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    int id = Integer.parseInt(idStr);

                    // CORREÇÃO: Usar GastoDAO em vez de UsuarioDAO
                    GastoDAO dao = new GastoDAO();
                    dao.deletar(id);

                    JOptionPane.showMessageDialog(null, "Gasto deletado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

    }
}
