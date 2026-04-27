package model;

import java.sql.Date;

public class Gasto {
    private int id;
    private double valor;
    private String descricao;
    private Date data;
    private int idUsuario;
    private int idPlanejamento;
    private int idEstabelecimento;
    private int idCategoria;
    private int idFormaPagamento;

    public Gasto() {}

    public Gasto(double valor, String descricao, Date data, int idUsuario, int idPlanejamento, int idEstabelecimento, int idCategoria, int idFormaPagamento) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.idUsuario = idUsuario;
        this.idPlanejamento = idPlanejamento;
        this.idEstabelecimento = idEstabelecimento;
        this.idCategoria = idCategoria;
        this.idFormaPagamento = idFormaPagamento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdPlanejamento() { return idPlanejamento; }
    public void setIdPlanejamento(int idPlanejamento) { this.idPlanejamento = idPlanejamento; }

    public int getIdEstabelecimento() { return idEstabelecimento; }
    public void setIdEstabelecimento(int idEstabelecimento) { this.idEstabelecimento = idEstabelecimento; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getIdFormaPagamento() { return idFormaPagamento; }
    public void setIdFormaPagamento(int idFormaPagamento) { this.idFormaPagamento = idFormaPagamento; }
}