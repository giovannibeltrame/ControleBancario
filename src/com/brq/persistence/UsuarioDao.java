package com.brq.persistence;

import java.sql.Types;

import com.brq.model.Usuario;

public class UsuarioDao extends Dao {

	public String cadastrar(Usuario u) throws Exception {
		open();
		
		call = con.prepareCall("{call incluir(?, ?, ?, ?, ?, ?)}");
		call.setString(1, u.getNome());
		call.setString(2, u.getCpf());
		if (u.getTelefone() != null)
			call.setString(3, u.getTelefone());
		else
			call.setNull(3, Types.VARCHAR);
		call.setString(4, u.getLogin());
		call.setString(5, u.getSenha());
		call.registerOutParameter(6, Types.VARCHAR);
		call.execute();
		
		String resp = "";
		if (call.getString(6) != null) {
			resp = call.getString(6);
		}
		
		close();
		return resp;
	}
	
	public Usuario buscarPorId(Integer id) throws Exception {
		open();
		
		Usuario u = null;
		stmt = con.prepareStatement("select * from usuario where idusuario = ?");
		stmt.setInt(1, id);		
		rs = stmt.executeQuery();
		if (rs.next()) {
			u = new Usuario(rs.getInt("idusuario"), rs.getString("cpf"), rs.getString("nome"), rs.getString("telefone"), rs.getString("login"), rs.getString("senha"));
		}
		
		close();
		return u;
	}
	
	public Usuario buscarPorCpf(String cpf) throws Exception {
		open();
		
		Usuario u = null;
		stmt = con.prepareStatement("select * from usuario where cpf = ?");
		stmt.setString(1, cpf);
		rs = stmt.executeQuery();
		if (rs.next()) {
			u = new Usuario(rs.getInt("idusuario"), rs.getString("cpf"), rs.getString("nome"), rs.getString("telefone"), rs.getString("login"), rs.getString("senha"));
		}
		
		close();
		return u;
	}
	
	public Integer logar(String login, String senha) throws Exception {
		open();
		
		stmt = con.prepareStatement("select logar(?,?) as total from dual");
		stmt.setString(1, login);
		stmt.setString(2, senha);
		rs = stmt.executeQuery();
		
		int opc = 0;
		if(rs.next()){
			opc = rs.getInt("total");
		}
		
		close();
		return opc;
	}
}