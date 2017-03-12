package com.brq.model;

public class Usuario {

	private Integer idusuario;
	private String cpf;
	private String nome;
	private String telefone;
	private String login;
	private String senha;
	
	public Usuario(Integer idusuario, String cpf, String nome, String telefone, String login, String senha) {
		super();
		this.idusuario = idusuario;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.login = login;
		this.senha = senha;
	}
	public Usuario() {
		super();
	}
	
	public Integer getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", cpf=" + cpf + ", nome=" + nome + ", telefone=" + telefone
				+ ", login=" + login + ", senha=" + senha + "]";
	}
}
