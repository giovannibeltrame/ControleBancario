package com.brq.model;

public class Gerente {

	private Integer idgerente;
	private Agencia agencia;
	private Usuario usuario;
	
	public Gerente(Integer idgerente, Agencia agencia, Usuario usuario) {
		super();
		this.idgerente = idgerente;
		this.agencia = agencia;
		this.usuario = usuario;
	}
	public Gerente() {
		super();
	}
	
	public Integer getIdgerente() {
		return idgerente;
	}
	public void setIdgerente(Integer idgerente) {
		this.idgerente = idgerente;
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Gerente [idgerente=" + idgerente + ", agencia=" + agencia + ", usuario=" + usuario + "]";
	}
}
