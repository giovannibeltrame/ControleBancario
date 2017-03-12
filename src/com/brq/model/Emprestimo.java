package com.brq.model;

public class Emprestimo {

	private Integer idemprestimo;
	private String descricao;
	private Double valortotal;
	private Double valorparcela;
	private Double juros;
	private Integer parcelas;
	
	public Emprestimo(Integer idemprestimo, String descricao, Double valortotal, Double valorparcela, Double juros,
			Integer parcelas) {
		super();
		this.idemprestimo = idemprestimo;
		this.descricao = descricao;
		this.valortotal = valortotal;
		this.valorparcela = valorparcela;
		this.juros = juros;
		this.parcelas = parcelas;
	}
	public Emprestimo() {
		super();
	}
	
	public Integer getIdemprestimo() {
		return idemprestimo;
	}
	public void setIdemprestimo(Integer idemprestimo) {
		this.idemprestimo = idemprestimo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValortotal() {
		return valortotal;
	}
	public void setValortotal(Double valortotal) {
		this.valortotal = valortotal;
	}
	public Double getValorparcela() {
		return valorparcela;
	}
	public void setValorparcela(Double valorparcela) {
		this.valorparcela = valorparcela;
	}
	public Double getJuros() {
		return juros;
	}
	public void setJuros(Double juros) {
		this.juros = juros;
	}
	public Integer getParcelas() {
		return parcelas;
	}
	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
	@Override
	public String toString() {
		return "Emprestimo [idemprestimo=" + idemprestimo + ", descricao=" + descricao + ", valortotal=" + valortotal
				+ ", valorparcela=" + valorparcela + ", juros=" + juros + ", parcelas=" + parcelas + "]";
	}
}
