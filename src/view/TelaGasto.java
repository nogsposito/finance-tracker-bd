package view;

import dao.GastoDAO;
import model.Gasto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollPane;

    public TelaGasto() {
        setTitle("Tela de Gasto");
        setSize(650, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel labelValor = new JLabel("Valor:");
        labelValor.setBounds(50, 20, 160, 30);
        add(labelValor);
        valor = new JTextField();
        valor.setBounds(50, 50, 440, 30);
        add(valor);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 80, 160, 30);
        add(labelDescricao);
        descricao = new JTextField();
        descricao.setBounds(50, 100, 440, 30);
        add(descricao);

        JLabel labelData = new JLabel("Data:");
        labelData.setBounds(50, 130, 160, 30);
        add(labelData);
        data = new JTextField();
        data.setBounds(50, 150, 440, 30);
        add(data);

        JLabel labelUsuarioId = new JLabel("ID do Usuário:");
        labelUsuarioId.setBounds(50, 180, 160, 30);
        add(labelUsuarioId);
        usuarioId = new JTextField();
        usuarioId.setBounds(50, 200, 440, 30);
        add(usuarioId);

        JLabel labelPlanejamentoId = new JLabel("ID do Planejamento:");
        labelPlanejamentoId.setBounds(50, 230, 160, 30);
        add(labelPlanejamentoId);
        planejamentoId = new JTextField();
        planejamentoId.setBounds(50, 250, 440, 30);
        add(planejamentoId);

        JLabel labelEstabelecimentoId = new JLabel("ID do Estabelecimento:");
        labelEstabelecimentoId.setBounds(50, 280, 160, 30);
        add(labelEstabelecimentoId);
        estabelecimentoId = new JTextField();
        estabelecimentoId.setBounds(50, 300, 440, 30);
        add(estabelecimentoId);

        JLabel labelCategoriaId = new JLabel("ID da Categoria:");
        labelCategoriaId.setBounds(50, 330, 160, 30);
        add(labelCategoriaId);
        categoriaId = new JTextField();
        categoriaId.setBounds(50, 350, 440, 30);
        add(categoriaId);

        JLabel labelFormaPagamentoId = new JLabel("ID da Forma de Pagamento:");
        labelFormaPagamentoId.setBounds(50, 380, 160, 30);
        add(labelFormaPagamentoId);
        formaPagamentoId = new JTextField();
        formaPagamentoId.setBounds(50, 400, 440, 30);
        add(formaPagamentoId);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Valor", "Descrição", "Data", "ID Usuário", "Categoria"}, 0);
        tabela = new JTable(modeloTabela);
        scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(50, 500, 500, 200);
        add(scrollPane);

        carregarTabela();

        JButton inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 455, 100, 30);
        add(inserirButton);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.setBounds(160, 455, 100, 30);
        add(deletarButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(270, 455, 100, 30);
        add(atualizarButton);

        JButton verLogsButton = new JButton("Ver Logs de Exclusão (Trigger)");
        verLogsButton.setBounds(380, 455, 220, 30);
        add(verLogsButton);

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

        verLogsButton.addActionListener(e -> {
            StringBuilder logs = new StringBuilder("Histórico de Exclusões (LogGasto):\n\n");
            String sql = "SELECT * FROM loggasto ORDER BY data_modificacao DESC LIMIT 10";

            try (java.sql.Connection conn = util.Conexao.conectar();
                 java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                 java.sql.ResultSet rs = stmt.executeQuery()) {

                boolean temLog = false;
                while (rs.next()) {
                    temLog = true;
                    logs.append("Ação: ").append(rs.getString("acao"))
                            .append(" | ID Gasto Apagado: ").append(rs.getInt("id_gasto_excluido"))
                            .append(" | Valor: R$ ").append(rs.getDouble("valor_antigo"))
                            .append(" | Data: ").append(rs.getTimestamp("data_modificacao")).append("\n");
                }

                if (!temLog) logs.append("Nenhum gasto foi excluído ainda.");

                // Mostra o resultado do trigger em uma janela de texto rolável
                JTextArea textArea = new JTextArea(logs.toString());
                textArea.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Efeito do Trigger", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar logs: " + ex.getMessage());
            }
        });


    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            GastoDAO dao = new GastoDAO();
            for (Gasto g : dao.listar()) {
                modeloTabela.addRow(new Object[]{
                        g.getId(), g.getValor(), g.getDescricao(), g.getData(),
                        g.getIdUsuario(), g.getIdCategoria()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

}
