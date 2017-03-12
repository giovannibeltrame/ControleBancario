package com.brq.persistence;

import java.sql.Date;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.brq.model.Conta;
import com.brq.model.Transacao;

public class TransacaoDao extends Dao {
	
	public void cadastrar(Transacao t) throws Exception {
		open();
	
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.util.Date data = new java.util.Date();	
		
		stmt = con.prepareStatement("insert into transacao values(seqtransacao.nextval, ?,?,?,?,?,?,?,?,?)");
		stmt.setDouble(1, t.getValor());
		stmt.setString(2, t.getTipo());
		stmt.setString(3, dateFormat.format(data));
		stmt.setString(4, t.getDescricao());
		if (t.getJuros() != null)
			stmt.setDouble(5, t.getJuros());
		else
			stmt.setNull(5, Types.DOUBLE);
		if (t.getContaOrigem() != null) {
			stmt.setInt(6, t.getContaOrigem().getIdconta());
			stmt.setInt(7, t.getContaOrigem().getAgencia().getIdagencia());	
		}
		else {
			stmt.setNull(6, Types.INTEGER);
			stmt.setNull(7, Types.INTEGER);
		}
		if (t.getContaDestino() != null) {
			stmt.setInt(8, t.getContaDestino().getIdconta());
			stmt.setInt(9, t.getContaDestino().getAgencia().getIdagencia());	
		}
		else {
			stmt.setNull(8, Types.INTEGER);
			stmt.setNull(9, Types.INTEGER);
		}
		stmt.execute();
		if (t.getContaOrigem() != null) {
			stmt = con.prepareStatement("insert into saldohistorico values (?, ?, ?, ?)");
			stmt.setInt(1, t.getContaOrigem().getIdconta());
			stmt.setInt(2, t.getContaOrigem().getAgencia().getIdagencia());
			stmt.setString(3, dateFormat.format(data));
			stmt.setDouble(4, t.getContaOrigem().getSaldo());
			stmt.execute();
		}
		if (t.getContaDestino() != null) {
			stmt = con.prepareStatement("insert into saldohistorico values (?, ?, ?, ?)");
			stmt.setInt(1, t.getContaDestino().getIdconta());
			stmt.setInt(2, t.getContaDestino().getAgencia().getIdagencia());
			stmt.setString(3, dateFormat.format(data));
			stmt.setDouble(4, t.getContaDestino().getSaldo());
			stmt.execute();
		}
		
		close();
	}
	
	public List<Transacao> buscar(Conta c, Integer dias) throws Exception {
		open();
		
		List<Transacao> lista = new ArrayList<Transacao>();
		stmt = con.prepareStatement("select * from transacao where ((idcontaorigem = ? and idagenciaorigem = ?)"
																	+ "or (idcontadestino = ? and idagenciadestino = ?))"
																	+ "and data >= current_date - ?");
		stmt.setInt(1, c.getIdconta());
		stmt.setInt(2, c.getAgencia().getIdagencia());
		stmt.setInt(3, c.getIdconta());
		stmt.setInt(4, c.getAgencia().getIdagencia());
		stmt.setInt(5, dias);
		rs = stmt.executeQuery();
		while (rs.next()) {
			Conta contaOrigem = new ContaDao().buscarPorId(rs.getInt("idcontaorigem"), rs.getInt("idagenciaorigem"));
			Conta contaDestino = new ContaDao().buscarPorId(rs.getInt("idcontadestino"), rs.getInt("idagenciadestino"));
			Transacao t = new Transacao(rs.getInt("idtransacao"), rs.getDouble("valor"), rs.getString("tipo"), rs.getDate("data"),
										rs.getString("descricao"), rs.getDouble("juros"), contaOrigem, contaDestino);
			lista.add(t);
		}
		
		close();
		return lista;
	}
	
	public Double totalEmprestado() throws Exception {
		open();
		stmt = con.prepareStatement("select sum(valor) as total from transacao where descricao like 'Emprestimo'");
		rs = stmt.executeQuery();
		rs.next();
		Double valor = rs.getDouble("total");
		close();
		return valor;
	}
	
	public Double totalQuitado() throws Exception {
		open();
		stmt = con.prepareStatement("select sum(valor) as total from transacao where descricao like 'Parcela Emprestimo'");
		rs = stmt.executeQuery();
		rs.next();
		Double valor = rs.getDouble("total");
		close();
		return valor;
	}
	
	
}
