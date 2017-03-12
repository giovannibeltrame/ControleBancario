package com.brq.model;

public class Cliente {

	private Integer idcliente;
	private Gerente gerente;
	private Usuario usuario;
	
	public Cliente(Integer idcliente, Gerente gerente, Usuario usuario) {
		super();
		this.idcliente = idcliente;
		this.gerente = gerente;
		this.usuario = usuario;
	}
	public Cliente() {
		super();
	}
	
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	public Gerente getGerente() {
		return gerente;
	}
	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Cliente [idcliente=" + idcliente + ", gerente=" + gerente + ", usuario=" + usuario + "]";
	}
}
