package model;

import java.sql.Date;

public class Planejamento {

    private int idPlanejamento;
    private String nome;
    private double valorLimite;
    private Date dataInicio;
    private Date dataFim;
    private int idUsuario;

    public Planejamento() {}

    public Planejamento(int idPlanejamento, String nome, double valorLimite, Date dataInicio, Date dataFim, int idUsuario) {
        this.idPlanejamento = idPlanejamento;
        this.nome = nome;
        this.valorLimite = valorLimite;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdPlanejamento() {
        return idPlanejamento;
    }

    public void setIdPlanejamento(int idPlanejamento) {
        this.idPlanejamento = idPlanejamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
