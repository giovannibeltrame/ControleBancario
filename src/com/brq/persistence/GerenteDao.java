package com.brq.persistence;

import com.brq.model.Agencia;
import com.brq.model.Gerente;
import com.brq.model.Usuario;

public class GerenteDao extends Dao {

	public Gerente buscarPorId(String nomeid, Integer id) throws Exception {
		open();
		
		Gerente g = null;
		stmt = (nomeid.equals("idusuario")) ? con.prepareStatement("select * from gerente where idusuario = ?")
											: con.prepareStatement("select * from gerente where idgerente = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if (rs.next()) {
			Agencia a = new AgenciaDao().buscarPorId(rs.getInt("idagencia"));
			Usuario u = new UsuarioDao().buscarPorId(rs.getInt("idusuario"));
			g = new Gerente(rs.getInt("idgerente"), a, u);
		}
		
		close();
		return g;
	}
}
