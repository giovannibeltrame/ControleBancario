package com.brq.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.brq.model.Conta;
import com.brq.model.Emprestimo;
import com.brq.model.Transacao;

public class EmprestimoDao extends Dao {

	public void cadastrar(Emprestimo e) throws Exception {
		open();
		
		stmt = con.prepareStatement("insert into emprestimo values (seqemprestimo.nextval, ?, ?, ?, ?, ?)");
		stmt.setString(1, e.getDescricao());
		stmt.setDouble(2, e.getValortotal());
		stmt.setDouble(3, e.getValorparcela());
		stmt.setDouble(4, e.getJuros());
		stmt.setInt(5, e.getParcelas());
		stmt.execute();
		
		close();
	}
	
	public void editar(Emprestimo e) throws Exception {
		open();
		
		stmt = con.prepareStatement("update emprestimo set descricao = ?, valortotal = ?, valorparcela = ?, juros = ?, parcelas = ? where idemprestimo = ?");
		stmt.setString(1, e.getDescricao());
		stmt.setDouble(2, e.getValortotal());
		stmt.setDouble(3, e.getValorparcela());
		stmt.setDouble(4, e.getJuros());
		stmt.setInt(5, e.getParcelas());
		stmt.setInt(6, e.getIdemprestimo());
		stmt.execute();
		
		close();
	}
	
	public List<Emprestimo> listar() throws Exception {
		open();
		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		
		stmt = con.prepareStatement("select * from emprestimo");
		rs = stmt.executeQuery();
		while (rs.next()) {
			Emprestimo e = new Emprestimo(rs.getInt("idemprestimo"), rs.getString("descricao"), rs.getDouble("valortotal"), rs.getDouble("valorparcela"), rs.getDouble("juros"), rs.getInt("parcelas"));
			lista.add(e);
		}
		
		close();
		return lista;
	}
	
	public List<Emprestimo> buscarPorDescricao(String desc) throws Exception {
		open();
		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		
		stmt = con.prepareStatement("select * from emprestimo where descricao like ?");
		stmt.setString(1, "%" + desc + "%");
		rs = stmt.executeQuery();
		while (rs.next()) {
			Emprestimo e = new Emprestimo(rs.getInt("idemprestimo"), rs.getString("descricao"), rs.getDouble("valortotal"), rs.getDouble("valorparcela"), rs.getDouble("juros"), rs.getInt("parcelas"));
			lista.add(e);
		}
		
		close();
		return lista;
	}
	
	public Emprestimo buscarPorId(Integer id) throws Exception {
		open();
		
		Emprestimo e = null;
		stmt = con.prepareStatement("select * from emprestimo where idemprestimo = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if (rs.next()) {
			e = new Emprestimo(rs.getInt("idemprestimo"), rs.getString("descricao"), rs.getDouble("valortotal"), rs.getDouble("valorparcela"), rs.getDouble("juros"), rs.getInt("parcelas"));
		}
		
		close();
		return e;
	}
	
	public void debitaParcelas(Emprestimo e) throws Exception {
		open();
		
		stmt = con.prepareStatement("select * from transacao where tipo = 'E' and valor = ? and juros = ?");
		stmt.setDouble(1, e.getValortotal());
		stmt.setDouble(2, e.getJuros());
		rs = stmt.executeQuery();
		while (rs.next()) {
			Conta c = new ContaDao().buscarPorId(rs.getInt("idcontadestino"), rs.getInt("idagenciadestino"));
			if (c.getParcelasapagar() > 0) {
				new ContaDao().sacar(c, e.getValorparcela());
				stmt = con.prepareStatement("update conta set parcelasapagar = ? where idconta = ? and idagencia = ?");
				stmt.setInt(1, c.getParcelasapagar() - 1);
				stmt.setInt(2, c.getIdconta());
				stmt.setInt(3, c.getAgencia().getIdagencia());
				stmt.execute();
				Transacao t = new Transacao(null, e.getValorparcela(), "E", new Date(), "Parcela Emprestimo", null, c, null);
				new TransacaoDao().cadastrar(t);
			}
		}
		
		close();
	}
}
