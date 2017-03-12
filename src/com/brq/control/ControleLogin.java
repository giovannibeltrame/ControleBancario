package com.brq.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jcommon.encryption.SimpleMD5;

import com.brq.model.Usuario;
import com.brq.persistence.ClienteDao;
import com.brq.persistence.GerenteDao;
import com.brq.persistence.UsuarioDao;

@WebServlet({"/ControleLogin", "/logar.html"})
public class ControleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControleLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			SimpleMD5 md5 = new SimpleMD5("brq", senha);
			String novasenha = md5.toHexString();
			System.out.println("NOVA SENHA: " + novasenha);
			UsuarioDao ud = new UsuarioDao();
			Integer id = ud.logar(login, novasenha);
			
			if(id > 0){
				Usuario user = ud.buscarPorId(id);
				HttpSession session = request.getSession();
				//user.setSenha(null);
				session.setAttribute("u", user);
				if ((new GerenteDao().buscarPorId("idusuario", id)) != null)
					response.sendRedirect("banco/pages/gerente/");
				else if ((new ClienteDao().buscarPorId("idusuario", id)) != null)
					response.sendRedirect("banco/");
			}
			else{
				request.setAttribute("msg", "<div class='alert alert-warning'>Usuário inválido!</div>");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro!</div>");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}	
	}
}
