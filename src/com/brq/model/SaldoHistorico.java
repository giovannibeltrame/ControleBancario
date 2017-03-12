package com.brq.model;

import java.util.Date;

public class SaldoHistorico {

	private Conta conta;
	private Date data;
	private Double saldo;
	
	public SaldoHistorico(Conta conta, Date data, Double saldo) {
		super();
		this.conta = conta;
		this.data = data;
		this.saldo = saldo;
	}
	public SaldoHistorico() {
		super();
	}
	
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
		return "SaldoHistorico [conta=" + conta + ", data=" + data + ", saldo=" + saldo + "]";
	}
}
