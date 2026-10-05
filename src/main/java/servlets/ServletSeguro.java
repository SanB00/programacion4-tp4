package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeguroDao;
import dao.TipoSegurosDao;
import dominio.Seguro;
import dominio.TipoSeguros;

@WebServlet("/ServletSeguro")
public class ServletSeguro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SeguroDao seguroDao = new SeguroDao();
    private final TipoSegurosDao tipoDao = new TipoSegurosDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			String param = request.getParameter("Param");

			if ("agregar".equals(param)) {
				mostrarAgregar(request, response);
			} else {
				response.sendRedirect("Inicio.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");

			if (request.getParameter("btnAceptar") != null) {
				agregarSeguro(request, response);
			} else {
				doGet(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void mostrarAgregar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ArrayList<TipoSeguros> listaTipos = tipoDao.getTipoSeguros();
		request.setAttribute("listaTipos", listaTipos);
		request.setAttribute("proximoId", seguroDao.getProximoId());
		request.getRequestDispatcher("AgregarSeguro.jsp").forward(request, response);
	}

    private void agregarSeguro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String descripcion = request.getParameter("txtDescripcion");
		String idTipoStr = request.getParameter("ddlTipo");
		String contratacionStr = request.getParameter("txtCostoContratacion");
		String aseguradoStr = request.getParameter("txtCostoAsegurado");

		String error = null;
		int idTipo = 0;

		if (descripcion == null || descripcion.trim().isEmpty()) {
			error = "Debe ingresar una descripción.";
		}

		try {
			idTipo = Integer.parseInt(idTipoStr);
		} catch (NumberFormatException e) {
			error = "Debe seleccionar un tipo de seguro.";
		}

		BigDecimal costoContratacion = parsearPositivo(contratacionStr);
		BigDecimal costoAsegurado = parsearPositivo(aseguradoStr);
		if (costoContratacion == null || costoAsegurado == null) {
			error = "Los costos deben ser valores numéricos positivos (usar punto para los decimales).";
		}

		if (error == null) {
			Seguro seg = new Seguro(descripcion.trim(), idTipo, costoContratacion, costoAsegurado);
			if (seguroDao.agregarSeguro(seg) > 0) {
				request.setAttribute("mensaje", "Seguro agregado con éxito");
			} else {
				error = "No se pudo agregar el seguro. Verifique los datos ingresados.";
			}
		}

		if (error != null) {
			// Se devuelven los datos cargados para no tener que reescribirlos
			request.setAttribute("mensaje", error);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("idTipoSel", idTipoStr);
			request.setAttribute("costoContratacion", contratacionStr);
			request.setAttribute("costoAsegurado", aseguradoStr);
		}

		mostrarAgregar(request, response);
    }

    private BigDecimal parsearPositivo(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }

        try {
            BigDecimal valor = new BigDecimal(texto.trim());
            return valor.signum() > 0 ? valor : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
