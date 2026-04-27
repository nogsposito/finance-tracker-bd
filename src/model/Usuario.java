package model;

import java.sql.Date;

public class Usuario {
    private int id_usuario;
    private String nome;
    private String senha;
    private Date data_cadastro;

    public Usuario() {}
    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public int getId() { return id_usuario; }
    public void setId(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Date getData_cadastro() { return data_cadastro; }
    public void setData_cadastro(Date data_cadastro) { this.data_cadastro = data_cadastro; }

}