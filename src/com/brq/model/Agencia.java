package com.brq.model;

public class Agencia {

	private Integer idagencia;
	private String nome;
	private String telefone;
	private String endereco;
	
	public Agencia(Integer idagencia, String nome, String telefone, String endereco) {
		super();
		this.idagencia = idagencia;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
	}
	public Agencia() {
		super();
	}
	
	public Integer getIdagencia() {
		return idagencia;
	}
	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
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
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		return "Agencia [idagencia=" + idagencia + ", nome=" + nome + ", telefone=" + telefone + ", endereco="
				+ endereco + "]";
	}
}
