package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("App Financeiro - Menu Principal");
        setSize(400, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa inteiro ao fechar o menu
        setLocationRelativeTo(null); // Centraliza a janela na tela

        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Escolha uma opção:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        JButton btnUsuario = new JButton("Gerenciar Usuários");
        JButton btnGasto = new JButton("Gerenciar Gastos");
        JButton btnPlanejamento = new JButton("Gerenciar Planejamentos");
        JButton btnCategoria = new JButton("Gerenciar Categorias");
        JButton btnRelatorios = new JButton("Ver Relatórios");

        add(btnUsuario);
        add(btnGasto);
        add(btnPlanejamento);
        add(btnCategoria);
        add(btnRelatorios);

        btnUsuario.addActionListener((ActionEvent e) -> {
            new TelaUsuario().setVisible(true);
        });

        btnGasto.addActionListener((ActionEvent e) -> {
            new TelaGasto().setVisible(true);
        });

        btnPlanejamento.addActionListener((ActionEvent e) -> {
            new TelaPlanejamento().setVisible(true);
        });

        btnCategoria.addActionListener((ActionEvent e) -> {
            new TelaCategoria().setVisible(true);
        });

        btnRelatorios.addActionListener((ActionEvent e) -> {
            new TelaRelatorios().setVisible(true);
        });
    }

}
