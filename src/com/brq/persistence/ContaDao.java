package com.brq.persistence;

import java.sql.Types;

import com.brq.model.Agencia;
import com.brq.model.Cliente;
import com.brq.model.Conta;

public class ContaDao extends Dao {

	public void cadastrar(Conta c) throws Exception {
		open();
		
		stmt = con.prepareStatement("insert into conta values (seqconta.nextval, ?, ?, ?, ?, ?)");
		stmt.setInt(1, c.getAgencia().getIdagencia());
		stmt.setInt(2, c.getCliente().getIdcliente());
		stmt.setString(3, c.getTipo());
		stmt.setDouble(4, c.getSaldo());
		if (c.getParcelasapagar() != null)
			stmt.setInt(5, c.getParcelasapagar());
		else
			stmt.setNull(5, Types.INTEGER);	
		stmt.execute();
		
		close();
	}
	
	public Conta buscar(Integer idcliente, String tipo) throws Exception {
		open();
		
		Conta c = null;
		stmt = con.prepareStatement("select * from conta where idcliente = ? and tipo = ?");
		stmt.setInt(1, idcliente);
		stmt.setString(2, tipo);
		rs = stmt.executeQuery();
		if (rs.next()) {
			Agencia a = new AgenciaDao().buscarPorId(rs.getInt("idagencia"));
			Cliente cli = new ClienteDao().buscarPorId("idcliente", idcliente);
			c = new Conta(rs.getInt("idconta"), a, cli, rs.getString("tipo"), rs.getDouble("saldo"), rs.getInt("parcelasapagar"));
		}
		
		close();
		return c;
	}
	
	public Conta buscarPorId(Integer idconta, Integer idagencia) throws Exception {
		open();
		
		Conta c = null;
		stmt = con.prepareStatement("select * from conta where idconta = ? and idagencia = ?");
		stmt.setInt(1, idconta);
		stmt.setInt(2, idagencia);
		rs = stmt.executeQuery();
		if (rs.next()) {
			Agencia a = new AgenciaDao().buscarPorId(rs.getInt("idagencia"));
			Cliente cli = new ClienteDao().buscarPorId("idcliente", rs.getInt("idcliente"));
			c = new Conta(rs.getInt("idconta"), a, cli, rs.getString("tipo"), rs.getDouble("saldo"), rs.getInt("parcelasapagar"));
		}
		
		close();
		return c;
	}
	
	public Double depositar(Conta c, Double valor) throws Exception {
		open();
		
		Double novoSaldo = c.getSaldo() + valor;
		c.setSaldo(novoSaldo);
		stmt = con.prepareStatement("update conta set saldo = ? where idconta = ? and idagencia = ?");
		stmt.setDouble(1, novoSaldo);
		stmt.setInt(2, c.getIdconta());
		stmt.setInt(3, c.getAgencia().getIdagencia());
		stmt.execute();
		
		close();
		return novoSaldo;
	}
	
	public Double sacar(Conta c, Double valor) throws Exception {
		open();
		
		Double novoSaldo = c.getSaldo() - valor;
		c.setSaldo(novoSaldo);
		stmt = con.prepareStatement("update conta set saldo = ? where idconta = ? and idagencia = ?");
		stmt.setDouble(1, novoSaldo);
		stmt.setInt(2, c.getIdconta());
		stmt.setInt(3, c.getAgencia().getIdagencia());
		stmt.execute();
		
		close();
		return novoSaldo;
	}
	
	public Integer atualizarParcelas(Conta c, Integer parcelas) throws Exception {
		open();
		
		Integer newParcelas = (c.getParcelasapagar() != null) ? (c.getParcelasapagar() + parcelas) : (parcelas) ;
		c.setParcelasapagar(newParcelas);
		stmt = con.prepareStatement("update conta set parcelasapagar = ? where idconta = ? and idagencia = ?");
		stmt.setInt(1, newParcelas);
		stmt.setInt(2, c.getIdconta());
		stmt.setInt(3, c.getAgencia().getIdagencia());
		stmt.execute();
		
		close();
		return newParcelas;
	}
	
	public Integer totalContas() throws Exception {
		open();
		stmt = con.prepareStatement("select count(distinct idconta) as total from conta");
		rs = stmt.executeQuery();
		rs.next();
			Integer total = rs.getInt("total");
		close();
		return total;
	}
	
	public Double percCorrente() throws Exception {
		open();
		stmt = con.prepareStatement("select (select count(distinct idconta) from conta where tipo = 'C') as perc from dual");
		rs = stmt.executeQuery();
		rs.next();
		Double perc = rs.getDouble("perc");
		close();
		return perc;
	}
	
	public Double percPoupanca() throws Exception {
		open();
		stmt = con.prepareStatement("select (select count(distinct idconta) from conta where tipo = 'P') as perc from dual");
		rs = stmt.executeQuery();
		rs.next();
		Double perc = rs.getDouble("perc");
		close();
		return perc;
	}
}
