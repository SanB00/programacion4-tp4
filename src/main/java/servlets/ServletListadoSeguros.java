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

@WebServlet("/ServletListadoSeguros")
public class ServletListadoSeguros extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        SeguroDao seguroDao = new SeguroDao();
        TipoSegurosDao tipoDao = new TipoSegurosDao();

        try {
            ArrayList<Seguro> listaSeguros;
            String idTipoSeleccionado = request.getParameter("ddlTipoSeguro");

            if (request.getParameter("btnFiltrar") != null) {
                try {
                    int idTipo = Integer.parseInt(idTipoSeleccionado);
                    if (idTipo <= 0) {
                        throw new NumberFormatException();
                    }
                    listaSeguros = seguroDao.obtenerPorTipoSeguro(idTipo);
                    request.setAttribute("idTipoSeleccionado", idTipo);
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMensaje", "Debe seleccionar un tipo de seguro para filtrar.");
                    listaSeguros = seguroDao.getSeguros();
                }
            } else {
                listaSeguros = seguroDao.getSeguros();
            }

            ArrayList<TipoSeguros> listaTipoSeguros = tipoDao.getTipoSeguros();
            request.setAttribute("listaSeguros", listaSeguros);
            request.setAttribute("listaTipoSeguros", listaTipoSeguros);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMensaje", "No se pudo cargar la información de seguros.");
            request.setAttribute("listaSeguros", new ArrayList<Seguro>());
            try {
                request.setAttribute("listaTipoSeguros", tipoDao.getTipoSeguros());
            } catch (Exception ex) {
                request.setAttribute("listaTipoSeguros", new ArrayList<TipoSeguros>());
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/ListarSeguros.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
