package com.brq.persistence;

import com.brq.model.Cliente;
import com.brq.model.Gerente;
import com.brq.model.Usuario;

public class ClienteDao extends Dao {
	
	public void cadastrar(Cliente c) throws Exception {
		open();
		
		stmt = con.prepareStatement("insert into cliente values(seqcliente.nextval, ?, ?)");
		stmt.setInt(1, c.getGerente().getIdgerente());
		stmt.setInt(2, c.getUsuario().getIdusuario());
		stmt.execute();
		
		close();
	}

	public Cliente buscarPorId(String nomeid, Integer id) throws Exception {
		open();
		
		Cliente c = null;
		stmt = (nomeid.equals("idusuario")) ? con.prepareStatement("select * from cliente where idusuario = ?")
											: con.prepareStatement("select * from cliente where idcliente = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if (rs.next()) {
			Gerente g = new GerenteDao().buscarPorId("idgerente", rs.getInt("idgerente"));
			Usuario u = new UsuarioDao().buscarPorId(rs.getInt("idusuario"));
			c = new Cliente(rs.getInt("idcliente"), g, u);
		}
		
		close();
		return c;
	}
}
