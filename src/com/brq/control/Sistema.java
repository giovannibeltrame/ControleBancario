package com.brq.control;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.brq.model.SaldoHistorico;
import com.brq.model.Transacao;
import com.brq.model.Usuario;
import com.brq.persistence.ClienteDao;
import com.brq.persistence.ContaDao;
import com.brq.persistence.Dao;
import com.brq.persistence.EmprestimoDao;
import com.brq.persistence.GerenteDao;
import com.brq.persistence.SaldoHistoricoDao;
import com.brq.persistence.TransacaoDao;

import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet({"/Sistema", "/banco/pages/logout.html", "/banco/pages/gerente/logout.html", "/banco/pages/depositar.html",
			"/banco/pages/sacar.html", "/banco/pages/transferir.html", "/banco/pages/extrato.html", "/banco/pages/mostrarplanos.html",
			"/banco/pages/solicitaremprestimo.html", "/banco/pages/conta.html", "/banco/pages/relatorioextrato.html",
			"/banco/pages/relatorio.html"})
public class Sistema extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Sistema() {
        super();
        // TODO Auto-generated constructor stub
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
			
			if (url.equalsIgnoreCase("/banco/pages/logout.html") || url.equalsIgnoreCase("/banco/pages/gerente/logout.html")) {
				HttpSession session = request.getSession();
				session.removeAttribute("u");
				session.invalidate();
				response.sendRedirect( request.getContextPath() + "/");
			}
			else if (url.equalsIgnoreCase("/banco/pages/depositar.html")) {
				String tipo = request.getParameter("tipo");
				String valor = request.getParameter("valor").replace(',', '.');
				
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				Cliente c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
				Conta conta = new ContaDao().buscar(c.getIdcliente(), tipo);
				new ContaDao().depositar(conta, new Double(valor));
				Transacao t = new Transacao(null, new Double(valor), "D", new Date(), "Deposito", null, null, conta);
				new TransacaoDao().cadastrar(t);
				
				String result = "<b>Nº CONTA:</b> " + conta.getIdconta()
								+ "<br><b>AGÊNCIA:</b> " + conta.getAgencia().getIdagencia()
								+ "<br><b>NOVO SALDO:</b> " + nf.format(conta.getSaldo());
				
				request.setAttribute("msg", "<div class='alert alert-success'>" + result + "</div>");
				request.getRequestDispatcher("deposito.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/sacar.html")) {
				String tipo = request.getParameter("tipo");
				String valor = request.getParameter("valor").replace(',', '.');
				String senha = request.getParameter("pass");
				
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				SimpleMD5 md5 = new SimpleMD5("brq", senha);
				String novasenha = md5.toHexString();
				if (novasenha.equalsIgnoreCase(u.getSenha())) {
					Cliente c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
					Conta conta = new ContaDao().buscar(c.getIdcliente(), tipo);
					if (conta.getSaldo() >= new Double(valor)) {
						new ContaDao().sacar(conta, new Double(valor));
						Transacao t = new Transacao(null, new Double(valor), "S", new Date(), "Saque", null, conta, null);
						new TransacaoDao().cadastrar(t);
						
						String result = "<b>Nº CONTA:</b> " + conta.getIdconta()
										+ "<br><b>AGÊNCIA:</b> " + conta.getAgencia().getIdagencia()
										+ "<br><b>NOVO SALDO:</b> " + nf.format(conta.getSaldo());
										
						request.setAttribute("msg", "<div class='alert alert-success'>" + result + "</div>");
						request.getRequestDispatcher("saque.jsp").forward(request, response);
					}
					else {
						request.setAttribute("msg", "<div class='alert alert-danger'>Seu saldo é insuficiente.</div>");
						request.getRequestDispatcher("saque.jsp").forward(request, response);
					}
				}
				else {
					request.setAttribute("msg", "<div class='alert alert-danger'>Senha incorreta.</div>");
					request.getRequestDispatcher("saque.jsp").forward(request, response);
				}
			}
			else if (url.equalsIgnoreCase("/banco/pages/transferir.html")) {
				String tipo = request.getParameter("tipo");
				String idAgenciaDestino = request.getParameter("agencia");
				String idContaDestino = request.getParameter("conta");
				String valor = request.getParameter("valor").replace(',', '.');
				String senha = request.getParameter("pass");
				
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				SimpleMD5 md5 = new SimpleMD5("brq", senha);
				String novasenha = md5.toHexString();
				if (novasenha.equalsIgnoreCase(u.getSenha())) {
					Cliente c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
					Conta conta = new ContaDao().buscar(c.getIdcliente(), tipo);
					Conta contaDestino = new ContaDao().buscarPorId(new Integer(idContaDestino), new Integer(idAgenciaDestino));
					
					if (conta.getSaldo() >= new Double(valor)) {
						new ContaDao().sacar(conta, new Double(valor));
						new ContaDao().depositar(contaDestino, new Double(valor));
						Transacao t = new Transacao(null, new Double(valor), "T", new Date(), "Transferencia - Ag. " 
													+ contaDestino.getAgencia().getIdagencia() + " Conta " + contaDestino.getIdconta(), null,
													conta, contaDestino);
						new TransacaoDao().cadastrar(t);
						
						String result = "Transferência <b>efetuada</b> com sucesso!";
		
						request.setAttribute("msg", "<div class='alert alert-success'>" + result + "</div>");
						request.getRequestDispatcher("transferencia.jsp").forward(request, response);
					}
					else {
						request.setAttribute("msg", "<div class='alert alert-danger'>Seu saldo é insuficiente.</div>");
						request.getRequestDispatcher("transferencia.jsp").forward(request, response);
					}
				}
				else {
					request.setAttribute("msg", "<div class='alert alert-danger'>Senha incorreta.</div>");
					request.getRequestDispatcher("transferencia.jsp").forward(request, response);
				}
			}
			else if (url.equalsIgnoreCase("/banco/pages/extrato.html")) {
				String tipo = request.getParameter("tipo");
				Integer dias = new Integer(request.getParameter("dias"));
				
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				Cliente c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
				Conta conta = new ContaDao().buscar(c.getIdcliente(), tipo);
				List<Transacao> lista = new ArrayList<Transacao>();
				lista = new TransacaoDao().buscar(conta, dias);
				
				request.setAttribute("lista", lista);
				request.setAttribute("saldo", conta.getSaldo());
				request.getRequestDispatcher("extrato.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/mostrarplanos.html")) {
				List<Emprestimo> lista = new ArrayList<Emprestimo>();
				lista = new EmprestimoDao().listar();
				request.setAttribute("lista", lista);
				request.getRequestDispatcher("emprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/solicitaremprestimo.html")) {
				Integer idemprestimo = new Integer(request.getParameter("id"));
				Emprestimo e = new EmprestimoDao().buscarPorId(idemprestimo);
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				Cliente c = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
				Conta conta = new ContaDao().buscar(c.getIdcliente(), "C");
				
				if (conta.getParcelasapagar() > 0 ) {
					System.out.println(conta.getParcelasapagar());
					request.setAttribute("msg", "<div class='alert alert-danger'>Você possui um empréstimo não quitado!</div>");
				}
				else {
					new ContaDao().depositar(conta, e.getValortotal());
					new ContaDao().atualizarParcelas(conta, e.getParcelas());
					Transacao t = new Transacao(null, e.getValortotal(), "E", new Date(), "Emprestimo", e.getJuros(), null, conta);
					new TransacaoDao().cadastrar(t);
					request.setAttribute("msg", "<div class='alert alert-success'>Empréstimo realizado com sucesso!<br>"
							+ "<b>NOVO SALDO: </b>" + nf.format(conta.getSaldo()));
				}
				
				request.getRequestDispatcher("emprestimo.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/conta.html")) {
				HttpSession session = request.getSession();
				Usuario u = (Usuario) session.getAttribute("u");
				Cliente cli = new ClienteDao().buscarPorId("idusuario", u.getIdusuario());
				Conta c = new ContaDao().buscar(cli.getIdcliente(), "C");
				Conta p = new ContaDao().buscar(cli.getIdcliente(), "P");
				
				request.setAttribute("u", u);
				request.setAttribute("c", c);
				request.setAttribute("p", p);
				request.getRequestDispatcher("conta.jsp").forward(request, response);
			}
			else if (url.equalsIgnoreCase("/banco/pages/relatorioextrato.html")) {
				InputStream arquivo = getServletContext().getResourceAsStream("/reportextrato.jasper");
				Map<String, Object> params = new HashMap<String, Object>();
				HttpSession session = request.getSession();
				Usuario user = (Usuario) session.getAttribute("u");
				Gerente g = new GerenteDao().buscarPorId("idusuario", user.getIdusuario());
				Cliente cli = new ClienteDao().buscarPorId("idusuario", user.getIdusuario());
				String tipo = request.getParameter("tipo");
				Conta c = new ContaDao().buscar(cli.getIdcliente(), tipo);
				
				params.put("idconta", c.getIdconta());
				params.put("idagencia", c.getAgencia().getIdagencia());
				params.put("imagem", "http://localhost:8083/ControleBancario/leaf_banner_gray.png");
				Dao d = new Dao();
				d.open();
				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, params, d.con);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(pdf);
				outputStream.flush();
				
				d.con.close();
			}
			else if (url.equalsIgnoreCase("/banco/pages/relatorio.html")) {
				List<SaldoHistorico> listac = new ArrayList<SaldoHistorico>();
				List<SaldoHistorico> listap = new ArrayList<SaldoHistorico>();
				HttpSession session = request.getSession();
				Usuario user = (Usuario) session.getAttribute("u");
				Cliente cli = new ClienteDao().buscarPorId("idusuario", user.getIdusuario());
				Conta c = new ContaDao().buscar(cli.getIdcliente(), "C");
				Conta p = new ContaDao().buscar(cli.getIdcliente(), "P");
				listac = new SaldoHistoricoDao().buscar(c);
				listap = new SaldoHistoricoDao().buscar(p);
				request.setAttribute("cc", listac);
				request.setAttribute("cp", listap);
				request.getRequestDispatcher("relatorio.jsp").forward(request, response);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
