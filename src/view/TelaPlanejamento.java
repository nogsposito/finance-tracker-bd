package view;

import dao.PlanejamentoDAO;
import model.Planejamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaPlanejamento extends JFrame {

    // Campos de texto para a inserção (assim como telaNome e telaSenha)
    private JTextField telaNome;
    private JTextField telaValorLimite;
    private JTextField telaDataInicio;
    private JTextField telaDataFim;
    private JTextField telaIdUsuario;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollPane;

    // Botões
    private JButton analisarButton;
    private JButton inserirButton;
    private JButton atualizarButton;
    private JButton deletarButton;

    public TelaPlanejamento() {
        setTitle("Tela de Planejamento");
        setSize(610, 660); // Aumentei a altura para caber os 5 campos
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // 1. Campo Nome
        JLabel labelNome = new JLabel("Nome do Planejamento:");
        labelNome.setBounds(50, 20, 200, 30);
        add(labelNome);
        telaNome = new JTextField();
        telaNome.setBounds(50, 50, 500, 30);
        add(telaNome);

        // 2. Campo Valor Limite
        JLabel labelValor = new JLabel("Valor Limite (R$):");
        labelValor.setBounds(50, 80, 200, 30);
        add(labelValor);
        telaValorLimite = new JTextField();
        telaValorLimite.setBounds(50, 110, 500, 30);
        add(telaValorLimite);

        // 3. Campo Data Início
        JLabel labelDataInicio = new JLabel("Data Início (AAAA-MM-DD):");
        labelDataInicio.setBounds(50, 140, 200, 30);
        add(labelDataInicio);
        telaDataInicio = new JTextField();
        telaDataInicio.setBounds(50, 170, 500, 30);
        add(telaDataInicio);

        // 4. Campo Data Fim
        JLabel labelDataFim = new JLabel("Data Fim (AAAA-MM-DD):");
        labelDataFim.setBounds(50, 200, 200, 30);
        add(labelDataFim);
        telaDataFim = new JTextField();
        telaDataFim.setBounds(50, 230, 500, 30);
        add(telaDataFim);

        // 5. Campo ID Usuário
        JLabel labelIdUsuario = new JLabel("ID do Usuário Dono:");
        labelIdUsuario.setBounds(50, 260, 200, 30);
        add(labelIdUsuario);
        telaIdUsuario = new JTextField();
        telaIdUsuario.setBounds(50, 290, 500, 30);
        add(telaIdUsuario);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Limite", "Início", "Fim", "ID Usuário"}, 0);
        tabela = new JTable(modeloTabela);
        scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(50, 400, 500, 200);
        add(scrollPane);

        carregarTabela();

        // Inicialização dos Botões
        inserirButton = new JButton("Inserir");
        inserirButton.setBounds(50, 350, 100, 30);
        add(inserirButton);

        atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(160, 350, 100, 30);
        add(atualizarButton);

        deletarButton = new JButton("Deletar");
        deletarButton.setBounds(270, 350, 100, 30);
        add(deletarButton);

        analisarButton = new JButton("Analisar Estouro (Procedure)");
        analisarButton.setBounds(380, 350, 200, 30);
        add(analisarButton);

        // Formato para as datas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // --- AÇÕES DOS BOTÕES IDÊNTICAS AO SEU EXEMPLO ---

        analisarButton.addActionListener(e -> {
            try {
                // Pede o ID do planejamento através de um pop-up (Requisito da Procedure com Cursor)
                String input = JOptionPane.showInputDialog(this, "Digite o ID do Planejamento para analisar estouro:");
                if (input != null && !input.isEmpty()) {
                    int idSelecionado = Integer.parseInt(input);
                    PlanejamentoDAO dao = new PlanejamentoDAO();
                    String resultado = dao.analisarEstouro(idSelecionado);
                    JOptionPane.showMessageDialog(this, resultado, "Resultado do Procedimento", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um ID numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        inserirButton.addActionListener(e -> {
            try {
                // Coleta os dados digitados nos TextFields
                Planejamento novoPlan = new Planejamento();
                novoPlan.setNome(telaNome.getText());
                novoPlan.setValorLimite(Double.parseDouble(telaValorLimite.getText()));
                novoPlan.setDataInicio(new java.sql.Date(sdf.parse(telaDataInicio.getText()).getTime()));
                novoPlan.setDataFim(new java.sql.Date(sdf.parse(telaDataFim.getText()).getTime()));
                novoPlan.setIdUsuario(Integer.parseInt(telaIdUsuario.getText()));

                PlanejamentoDAO dao = new PlanejamentoDAO();
                dao.inserir(novoPlan);

                JOptionPane.showMessageDialog(null, "Planejamento inserido!");

                // Limpa os campos após inserir
                telaNome.setText("");
                telaValorLimite.setText("");
                telaDataInicio.setText("");
                telaDataFim.setText("");
                telaIdUsuario.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir: " + ex.getMessage());
            }
        });

        atualizarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("ID do planejamento a atualizar:");

                if (idStr != null && !idStr.trim().isEmpty()) {
                    // Pede todos os novos dados através de pop-ups
                    String novoNome = JOptionPane.showInputDialog("Novo Nome do Planejamento:");
                    String novoValor = JOptionPane.showInputDialog("Novo Valor Limite:");
                    String novaDataIni = JOptionPane.showInputDialog("Nova Data Início (AAAA-MM-DD):");
                    String novaDataFim = JOptionPane.showInputDialog("Nova Data Fim (AAAA-MM-DD):");
                    String novoIdUsu = JOptionPane.showInputDialog("Novo ID do Usuário Dono:");

                    if (novaDataIni == null || novaDataIni.trim().isEmpty() || novaDataFim == null || novaDataFim.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "As datas de Início e Fim não podem ficar vazias!");
                        return; // Impede o código de continuar e dar erro no parse
                    }

                    Planejamento planAtualizado = new Planejamento();
                    planAtualizado.setIdPlanejamento(Integer.parseInt(idStr)); // Seta o ID
                    planAtualizado.setNome(novoNome);
                    planAtualizado.setValorLimite(Double.parseDouble(novoValor));

                    planAtualizado.setDataInicio(new java.sql.Date(sdf.parse(novaDataIni.trim()).getTime()));
                    planAtualizado.setDataFim(new java.sql.Date(sdf.parse(novaDataFim.trim()).getTime()));

                    planAtualizado.setIdUsuario(Integer.parseInt(novoIdUsu));

                    PlanejamentoDAO dao = new PlanejamentoDAO();
                    dao.atualizar(planAtualizado);

                    JOptionPane.showMessageDialog(null, "Planejamento atualizado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Id do planejamento para deletar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    int id = Integer.parseInt(idStr);

                    // Manda pro DAO
                    PlanejamentoDAO dao = new PlanejamentoDAO();
                    dao.deletar(id);

                    JOptionPane.showMessageDialog(null, "Planejamento deletado!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            PlanejamentoDAO dao = new PlanejamentoDAO();
            for (Planejamento p : dao.listar()) {
                modeloTabela.addRow(new Object[]{
                        p.getIdPlanejamento(), p.getNome(), p.getValorLimite(),
                        p.getDataInicio(), p.getDataFim(), p.getIdUsuario()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

}
