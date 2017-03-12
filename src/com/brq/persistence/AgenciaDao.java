package com.brq.persistence;

import com.brq.model.Agencia;

public class AgenciaDao extends Dao {

	public Agencia buscarPorId(Integer id) throws Exception {
		open();
		
		Agencia a = null;
		stmt = con.prepareStatement("select * from agencia where idagencia = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if (rs.next()) {
			a = new Agencia(rs.getInt("idagencia"), rs.getString("nome"), rs.getString("telefone"), rs.getString("endereco"));
		}
		
		close();
		return a;
	}
}
