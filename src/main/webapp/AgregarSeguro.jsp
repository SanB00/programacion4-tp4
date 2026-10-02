<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dominio.TipoSeguros"%>
<%!
    // Declaración: escapa el texto antes de mostrarlo en el HTML
    private String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<%
    // Si se entra directo al JSP, se pasa primero por el servlet
    ArrayList<TipoSeguros> listaTipos = (ArrayList<TipoSeguros>) request.getAttribute("listaTipos");
    if (listaTipos == null) {
        response.sendRedirect("ServletSeguro?Param=agregar");
        return;
    }
    Object proximoId = request.getAttribute("proximoId");
    String mensaje = (String) request.getAttribute("mensaje");
    String descripcion = (String) request.getAttribute("descripcion");
    String idTipoSel = (String) request.getAttribute("idTipoSel");
    String costoContratacion = (String) request.getAttribute("costoContratacion");
    String costoAsegurado = (String) request.getAttribute("costoAsegurado");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agregar Seguros</title>
</head>
<body>
    <jsp:include page="Menu.jsp"></jsp:include>

    <h2>Agregar Seguros</h2>

    <form method="post" action="ServletSeguro">
        <table>
            <tr>
                <td>Id Seguro:</td>
                <td><%=proximoId%></td>
            </tr>
            <tr>
                <td>Descripción:</td>
                <td><input type="text" name="txtDescripcion" value="<%=esc(descripcion)%>"></td>
            </tr>
            <tr>
                <td>Tipo de Seguro:</td>
                <td><select name="ddlTipo">
                    <% for (TipoSeguros t : listaTipos) { %>
                    <option value="<%=t.getIdTipo()%>"
                        <%=String.valueOf(t.getIdTipo()).equals(idTipoSel) ? "selected" : ""%>>
                        <%=esc(t.getDescripcion())%>
                    </option>
                    <% } %>
                </select></td>
            </tr>
            <tr>
                <td>Costo contratación:</td>
                <td><input type="text" name="txtCostoContratacion" value="<%=esc(costoContratacion)%>"></td>
            </tr>
            <tr>
                <td>Costo Máximo Asegurado:</td>
                <td><input type="text" name="txtCostoAsegurado" value="<%=esc(costoAsegurado)%>"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="btnAceptar" value="Aceptar"></td>
            </tr>
        </table>
    </form>

    <% if (mensaje != null) { %>
    <p><%=esc(mensaje)%></p>
    <% } %>
</body>
</html>