package com.brq.model;

public class Conta {

	private Integer idconta;
	private Agencia agencia;
	private Cliente cliente;
	private String tipo;
	private Double saldo;
	private Integer parcelasapagar;
	
	public Conta(Integer idconta, Agencia agencia, Cliente cliente, String tipo, Double saldo, Integer parcelasapagar) {
		super();
		this.idconta = idconta;
		this.agencia = agencia;
		this.cliente = cliente;
		this.tipo = tipo;
		this.saldo = saldo;
		this.parcelasapagar = parcelasapagar;
	}
	public Conta() {
		super();
	}
	
	public Integer getIdconta() {
		return idconta;
	}
	public void setIdconta(Integer idconta) {
		this.idconta = idconta;
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}	
	public Integer getParcelasapagar() {
		return parcelasapagar;
	}
	public void setParcelasapagar(Integer parcelasapagar) {
		this.parcelasapagar = parcelasapagar;
	}
	
	@Override
	public String toString() {
		return "Conta [idconta=" + idconta + ", agencia=" + agencia + ", cliente=" + cliente + ", tipo=" + tipo
				+ ", saldo=" + saldo + "]";
	}
}
