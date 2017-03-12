package com.brq.control;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jcommon.encryption.SimpleMD5;

import com.brq.model.Cliente;
import com.brq.model.Conta;
import com.brq.model.Emprestimo;
import com.brq.model.Gerente;
import com.brq.model.Usuario;
import com.brq.persistence.ClienteDao;
import com.brq.persistence.ContaDao;
import com.brq.persistence.Dao;
import com.brq.persistence.EmprestimoDao;
import com.brq.persistence.GerenteDao;
import com.brq.persistence.TransacaoDao;
import com.brq.persistence.UsuarioDao;

import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet({"/ControleGerente", "/banco/pages/gerente/cadastrar.html", "/banco/pages/gerente/emprestimo.html",
			"/banco/pages/gerente/buscaremprestimo.html", "/banco/pages/gerente/editaremprestimo.html",
			"/banco/pages/gerente/editaremprestimo2.html", "/banco/pages/gerente/debitarparcelas.html",
			"/banco/pages/gerente/relatoriocliente.html", "/banco/pages/gerente/relatorioemprestimo.html",
			"/banco/pages/gerente/relatorio.html"})
public class ControleGerente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControleGerente() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String url = request.getServletPath();
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			
			if (url.equalsIgnoreCase("/banco/pages/gerente/cadastrar.html")) {
				String nome = request.getParameter("nome");
				String cpf = request.getParameter("cpf");
				String telefone = request.getParameter("telefone");
				String login = request.getParameter("login");
				String senha = request.getParameter("senha");
				
				SimpleMD5 md5 = new SimpleMD5("brq", senha);
				String novasenha = md5.toHexString();
				Usuario u = new Usuario(null, cpf, nome, telefone, login, novasenha);
				String result = new UsuarioDao().cadastrar(u);
				
				if (result.equalsIgnoreCase("Usuario cadastrado!")) {
					HttpSession session = request.getSession();
					Usuario gerente = (Usuario) session.getAttribute("u");
					Gerente g = new GerenteDao().buscarPorId("idusuario", gerente.getIdusuario());
					u = new UsuarioDao().buscarPorCpf(cpf);
					Cliente c = new Cliente(null, g, u);
					new ClienteDao().cadastrar(c);
					c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
					
					String[] contas = request.getParameterValues("conta");
					for (String tipo : contas) {
						Conta conta = new Conta(null, g.getAgencia(), c, tipo, 0., null);
						new ContaDao().cadastrar(conta);
					}
				}
				request.setAttribute("msg", "<div class='alert alert-info'>" + result + "</div>");
				request.getRequestDispatcher("novocliente.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/emprestimo.html")) {
				String descricao = request.getParameter("descricao");
				Double valortotal = new Double(request.getParameter("valortotal").replace(',', '.'));
				Double juros = new Double(request.getParameter("juros").replace(',', '.'));
				Integer parcelas = new Integer(request.getParameter("parcelas"));
				Double valorparcela = (valortotal + (valortotal * (juros / 100))) / parcelas;
				
				Emprestimo e = new Emprestimo(null, descricao, valortotal, valorparcela, juros, parcelas);
				new EmprestimoDao().cadastrar(e);
				
				request.setAttribute("msg", "<div class='alert alert-info'>Plano cadastrado com sucesso.<br>"
											+ "<b>Valor das Parcelas:</b> " + nf.format(valorparcela));
				request.getRequestDispatcher("novoemprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/buscaremprestimo.html")) {
				String descricao = request.getParameter("descricao");
				
				List<Emprestimo> lista = new ArrayList<Emprestimo>();
				lista = new EmprestimoDao().buscarPorDescricao(descricao);
				
				request.setAttribute("lista", lista);
				request.getRequestDispatcher("gerenciaremprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/editaremprestimo.html")) {
				Integer id = new Integer(request.getParameter("id"));
				Emprestimo e = new EmprestimoDao().buscarPorId(id);
				
				request.setAttribute("emp", e);
				request.getRequestDispatcher("editaremprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/editaremprestimo2.html")) {
				String descricao = request.getParameter("descricao");
				Double valortotal = new Double(request.getParameter("valortotal").replace(',', '.'));
				Double juros = new Double(request.getParameter("juros").replace(',', '.'));
				Integer parcelas = new Integer(request.getParameter("parcelas"));
				Double valorparcela = (valortotal + (valortotal * (juros / 100))) / parcelas;
				Integer idemprestimo = new Integer(request.getParameter("idemprestimo"));
				
				Emprestimo e = new Emprestimo(idemprestimo, descricao, valortotal, valorparcela, juros, parcelas);
				new EmprestimoDao().editar(e);
				
				request.setAttribute("msg", "<div class='alert alert-info'>Plano salvo com sucesso.</div>");
				request.getRequestDispatcher("editaremprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/debitarparcelas.html")) {
				Integer idemprestimo = new Integer(request.getParameter("id"));
				Emprestimo e = new EmprestimoDao().buscarPorId(idemprestimo);
				new EmprestimoDao().debitaParcelas(e);
				
				request.setAttribute("msg", "<div class='alert alert-success'>Parcelas debitadas com sucesso.</div>");
				request.getRequestDispatcher("gerenciaremprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/relatoriocliente.html")) {
				InputStream arquivo = getServletContext().getResourceAsStream("/reportclientes.jasper");
				Map<String, Object> params = new HashMap<String, Object>();
				HttpSession session = request.getSession();
				Usuario user = (Usuario) session.getAttribute("u");
				Gerente g = new GerenteDao().buscarPorId("idusuario", user.getIdusuario());
				
				params.put("idgerente", g.getIdgerente());
				params.put("imagem", "http://localhost:8083/ControleBancario/leaf_banner_gray.png");
				Dao d = new Dao();
				d.open();
				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, params, d.con);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(pdf);
				outputStream.flush();
				
				d.con.close();
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/relatorioemprestimo.html")) {
				InputStream arquivo = getServletContext().getResourceAsStream("/reportemprestimos.jasper");
				Map<String, Object> params = new HashMap<String, Object>();
				HttpSession session = request.getSession();
				Usuario user = (Usuario) session.getAttribute("u");
				Gerente g = new GerenteDao().buscarPorId("idusuario", user.getIdusuario());
				
				params.put("idgerente", g.getIdgerente());
				params.put("imagem", "http://localhost:8083/ControleBancario/leaf_banner_gray.png");
				Dao d = new Dao();
				d.open();
				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, params, d.con);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(pdf);
				outputStream.flush();
				
				d.con.close();
			}
			else if (url.equalsIgnoreCase("/banco/pages/gerente/relatorio.html")) {
				Double cc = new ContaDao().percCorrente();
				Double cp = new ContaDao().percPoupanca();
				Double tote = new TransacaoDao().totalEmprestado();
				Double totq = new TransacaoDao().totalQuitado();
				request.setAttribute("cc", cc);
				request.setAttribute("cp", cp);
				request.setAttribute("tote", tote);
				request.setAttribute("totq", totq);
				request.getRequestDispatcher("relatorio.jsp").forward(request, response);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
