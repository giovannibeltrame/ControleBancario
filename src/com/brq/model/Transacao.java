package com.brq.model;

import java.util.Date;

public class Transacao {

	private Integer idtransacao;
	private Double valor;
	private String tipo;
	private Date data;
	private String descricao;
	private Double juros;
	private Conta contaOrigem;
	private Conta contaDestino;
	
	public Transacao(Integer idtransacao, Double valor, String tipo, Date data, String descricao, Double juros,
			Conta contaOrigem, Conta contaDestino) {
		super();
		this.idtransacao = idtransacao;
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
		this.descricao = descricao;
		this.juros = juros;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}
	public Transacao() {
		super();
	}
	
	public Integer getIdtransacao() {
		return idtransacao;
	}
	public void setIdtransacao(Integer idtransacao) {
		this.idtransacao = idtransacao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getJuros() {
		return juros;
	}
	public void setJuros(Double juros) {
		this.juros = juros;
	}
	public Conta getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public Conta getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}	
	
	@Override
	public String toString() {
		return "Transacao [idtransacao=" + idtransacao + ", valor=" + valor + ", tipo=" + tipo + ", data=" + data
				+ ", descricao=" + descricao + ", juros=" + juros + ", contaOrigem=" + contaOrigem + ", contaDestino="
				+ contaDestino + "]";
	}
}
