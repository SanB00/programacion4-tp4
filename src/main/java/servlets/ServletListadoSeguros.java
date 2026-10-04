package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeguroDao;
import dao.TipoSegurosDao;
import dominio.Seguro;
import dominio.TipoSeguros;

/**
 * Servlet implementation class ServletListadoSeguros
 */
@WebServlet("/ServletListadoSeguros")
public class ServletListadoSeguros extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListadoSeguros() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Seguro> listaSeguros = new ArrayList<Seguro>();
		try {
			if (request.getParameter("param") != null) {
				// Cargamos seguros en tabla
				listaSeguros = new SeguroDao().getSeguros();
			}
			if (request.getParameter("btnFiltrar") != null) {
				int idTipo = Integer.parseInt(request.getParameter("ddlTipoSeguro"));
				listaSeguros = new SeguroDao().obtenerPorTipoSeguro(idTipo);
			}
			
			request.setAttribute("listaSeguros", listaSeguros);
			ArrayList<TipoSeguros> listaTipoSeguros = new TipoSegurosDao().getTipoSeguros();
			request.setAttribute("listaTipoSeguros", listaTipoSeguros);
		} catch (Exception e) {
			request.setAttribute("errorMensaje", "Error: " + e);
			e.printStackTrace();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("/ListarSeguros.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}