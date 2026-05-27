package view;

import util.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TelaRelatorios extends JFrame {

    private JComboBox<String> cbRelatorios;
    private JTextField telaFiltroId;
    private JButton btnGerar;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;

    public TelaRelatorios() {
        setTitle("Tela de Relatórios e Consultas");
        setSize(800, 600); // Tamanho maior para caber a tabela de resultados
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só essa janela
        setLayout(null); // Padrão de layout absoluto que você está usando

        // 1. Menu de Seleção de Relatórios
        JLabel labelCombo = new JLabel("Selecione o Relatório ou View:");
        labelCombo.setBounds(50, 20, 300, 30);
        add(labelCombo);

        String[] opcoes = {
                "Selecione uma opção...",
                "1. Gastos por Categoria > 100 (JOIN + GROUP BY + HAVING)",
                "2. Gastos por Usuário Específico (2 JOINS + WHERE)",
                "3. Usuários sem Gasto (ANTI-JOIN)",
                "4. Gastos Acima da Média (SUBCONSULTA)",
                "5. VIEW: Relatório de Gastos Detalhados",
                "6. VIEW: Total de Gastos por Usuário"
        };
        cbRelatorios = new JComboBox<>(opcoes);
        cbRelatorios.setBounds(50, 50, 500, 30);
        add(cbRelatorios);

        // 2. Campo de Filtro (Para o relatório 2)
        JLabel labelFiltro = new JLabel("ID do Usuário (Apenas para o Relatório 2):");
        labelFiltro.setBounds(50, 90, 300, 30);
        add(labelFiltro);

        telaFiltroId = new JTextField();
        telaFiltroId.setBounds(50, 120, 500, 30);
        add(telaFiltroId);

        // 3. Botão de Gerar
        btnGerar = new JButton("Gerar Relatório");
        btnGerar.setBounds(50, 160, 200, 30);
        add(btnGerar);

        // 4. Tabela Dinâmica
        modeloTabela = new DefaultTableModel();
        tabelaResultados = new JTable(modeloTabela);

        // Colocando a tabela dentro de um ScrollPane para ter barra de rolagem
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setBounds(50, 210, 680, 300);
        add(scrollPane);

        // --- AÇÃO DO BOTÃO GERAR ---
        btnGerar.addActionListener(e -> gerarRelatorio());
    }

    private void gerarRelatorio() {
        int indexSelecionado = cbRelatorios.getSelectedIndex();

        if (indexSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um relatório válido na lista.");
            return;
        }

        String sql = "";
        boolean precisaFiltro = false;

        // Associa a escolha do usuário às consultas SQL construídas na Etapa 04
        switch (indexSelecionado) {
            case 1:
                sql = "SELECT c.nome AS categoria, SUM(g.valor) AS total_gasto FROM gasto g JOIN categoria c ON g.id_categoria = c.id_categoria GROUP BY c.nome HAVING SUM(g.valor) > 100";
                break;
            case 2:
                sql = "SELECT u.nome AS usuario, g.descricao, g.valor, e.nome AS estabelecimento FROM gasto g JOIN usuario u ON g.id_usuario = u.id_usuario JOIN estabelecimento e ON g.id_estabelecimento = e.id_estabelecimento WHERE u.id_usuario = ?";
                precisaFiltro = true;
                break;
            case 3:
                sql = "SELECT u.nome as usuario_sem_gasto FROM usuario u LEFT JOIN gasto g on g.id_usuario = u.id_usuario WHERE g.id_gasto IS NULL";
                break;
            case 4:
                sql = "SELECT valor, descricao FROM gasto WHERE valor > (SELECT AVG(valor) FROM gasto)";
                break;
            case 5:
                sql = "SELECT * FROM vw_relatorio_gastos_detalhados";
                break;
            case 6:
                sql = "SELECT * FROM vw_total_gastos_usuario";
                break;
        }

        executarEPreencherTabela(sql, precisaFiltro);
    }

    private void executarEPreencherTabela(String sql, boolean precisaFiltro) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Se for o Relatório 2, precisamos pegar o ID que o usuário digitou na caixa de texto
            if (precisaFiltro) {
                String idTexto = telaFiltroId.getText().trim();
                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Para este relatório, você deve digitar o ID do Usuário no campo de filtro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                stmt.setInt(1, Integer.parseInt(idTexto));
            }

            // Executa a consulta
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numeroDeColunas = metaData.getColumnCount();

            // Limpa a tabela e configura as colunas dinamicamente com base no resultado do SQL
            modeloTabela.setRowCount(0);
            modeloTabela.setColumnCount(0);

            for (int i = 1; i <= numeroDeColunas; i++) {
                modeloTabela.addColumn(metaData.getColumnLabel(i).toUpperCase()); // Pega o nome ou 'alias' da coluna
            }

            // Preenche as linhas com os dados
            boolean temDados = false;
            while (rs.next()) {
                temDados = true;
                Object[] linha = new Object[numeroDeColunas];
                for (int i = 1; i <= numeroDeColunas; i++) {
                    linha[i - 1] = rs.getObject(i);
                }
                modeloTabela.addRow(linha);
            }

            if (!temDados) {
                JOptionPane.showMessageDialog(this, "A consulta foi realizada, mas não retornou nenhum registro.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O ID digitado deve ser numérico!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao executar o relatório: " + ex.getMessage(), "Erro no BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}