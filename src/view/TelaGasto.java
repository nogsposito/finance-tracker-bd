package view;

import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
        setSize(400, 300);
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
                Connection con = Conexao.conectar();

                String sql = "INSERT INTO Gasto (valor, descricao, data, id_usuario, id_planejamento, id_estabelecimento, id_categoria, id_forma_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setDouble(1, Double.parseDouble(valor.getText()));
                stmt.setString(2, descricao.getText());

                // CORRETO PARA DATA
                stmt.setDate(3, java.sql.Date.valueOf(data.getText()));

                stmt.setInt(4, Integer.parseInt(usuarioId.getText()));
                stmt.setInt(5, Integer.parseInt(planejamentoId.getText()));
                stmt.setInt(6, Integer.parseInt(estabelecimentoId.getText()));
                stmt.setInt(7, Integer.parseInt(categoriaId.getText()));
                stmt.setInt(8, Integer.parseInt(formaPagamentoId.getText()));

                stmt.executeUpdate();

                con.close();

                System.out.println("Gasto inserido!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        atualizarButton.addActionListener(e -> {
            try {
                Connection con = Conexao.conectar();

                String sql = "UPDATE Gasto SET valor = ?, descricao = ?, data = ?, id_usuario = ?, id_planejamento = ?, id_estabelecimento = ?, id_categoria = ?, id_forma_pagamento = ? WHERE id_gasto = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                String id = javax.swing.JOptionPane.showInputDialog("ID do gasto:");
                String novo_valor = javax.swing.JOptionPane.showInputDialog("Novo valor:");
                String nova_descricao = javax.swing.JOptionPane.showInputDialog("Nova descricao:");
                String nova_data = javax.swing.JOptionPane.showInputDialog("Nova data:");
                String novo_id_usuario = javax.swing.JOptionPane.showInputDialog("Novo ID usuario:");
                String novo_id_planejamento = javax.swing.JOptionPane.showInputDialog("Novo ID planejamento:");
                String novo_id_estabelecimento = javax.swing.JOptionPane.showInputDialog("Novo ID estabelecimento:");
                String novo_id_categoria = javax.swing.JOptionPane.showInputDialog("Novo ID categoria:");
                String novo_id_forma_pagamento = javax.swing.JOptionPane.showInputDialog("Novo ID forma_pagamento:");


                ps.setString(1, novo_valor);
                ps.setString(2, nova_descricao);
                ps.setString(3, nova_data);
                ps.setString(4, novo_id_usuario);
                ps.setString(5, novo_id_planejamento);
                ps.setString(6, novo_id_estabelecimento);
                ps.setString(7, novo_id_categoria);
                ps.setString(8, novo_id_forma_pagamento);
                ps.setInt(9, Integer.parseInt(id));

                ps.executeUpdate();

                con.close();

                System.out.println("Gasto atualizado!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        deletarButton.addActionListener(e -> {
            try {
                Connection con = Conexao.conectar();

                String sql = "DELETE FROM Gasto WHERE id_gasto = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                String id = javax.swing.JOptionPane.showInputDialog("ID do gasto:");
                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                con.close();

                System.out.println("Gasto deletado!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}
