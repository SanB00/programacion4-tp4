<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dominio.Seguro, dao.SeguroDao"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="dominio.TipoSeguros, java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP4 - Lista de Seguros</title>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<%
	//obtenemos lista de tipos de seguros para mostrar en ddl
	ArrayList<TipoSeguros> listaTipoSeguros = null;
	ArrayList<Seguro> listaSeguros = null;
	if (request.getAttribute("listaTipoSeguros") != null) {
		listaTipoSeguros = (ArrayList<TipoSeguros>) request.getAttribute("listaTipoSeguros");
	}
	if (request.getAttribute("listaSeguros") != null) {
		listaSeguros = (ArrayList<Seguro>) request.getAttribute("listaSeguros");
	}
	%>
	<h1>Listar seguros</h1>

	<c:if test="${not empty errorMensaje}">
		<div
			style="color: red; background-color: #ffe6e6; padding: 10px; border: 1px solid red; margin-bottom: 15px;">
			<strong>¡Atención!</strong> ${errorMensaje}
		</div>
	</c:if>


	<form action="ServletListadoSeguros">
		Filtrar por tipo de seguro: <select name="ddlTipoSeguro"
			class="form-control">
			<option value="-1">-- Seleccione una opción --</option>
			<c:forEach var="ts" items="${listaTipoSeguros}">
				<option value="${ts.idTipo}">${ts.descripcion}</option>
			</c:forEach>
		</select> <input type="submit" value="Filtrar" name="btnFiltrar"> <input
			type="submit" value="Mostrar todos" name="btnMostrarTodos"> <br>
		<table border="1">
			<tr>
				<th>ID Seguro</th>
				<th>Descripcion</th>
				<th>ID Tipo</th>
				<th>Tipo de Seguro</th>
				<th>Costo Contratacion</th>
				<th>Costo Asegurado</th>
			</tr>
			<%
			if (listaSeguros != null)
				for (Seguro s : listaSeguros) {
			%>
			<tr>
				<td><%=s.getIdSeguro()%></td>
				<td><%=s.getDescripcion()%></td>
				<td><%=s.getIdTipo()%></td>
				<td><%=s.getDescripcionTipoSeguro()%></td>
				<td><%=s.getCostoContratacion()%></td>
				<td><%=s.getCostoAsegurado()%></td>
				<%
				}
				%>
			
		</table>
	</form>
</body>
</html>