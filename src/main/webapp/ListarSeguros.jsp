<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dominio.Seguro, dao.SeguroDao"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="dominio.TipoSeguros, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<%
	//obtenemos lista de tipos de seguros para mostrar en ddl
	ArrayList<TipoSeguros> lts=null;
	ArrayList<Seguro> ls=null;
	if(request.getAttribute("lts")!=null){
		lts=(ArrayList<TipoSeguros>)request.getAttribute("lts");
	}
	if(request.getAttribute("ls")!=null){
		ls=(ArrayList<Seguro>)request.getAttribute("ls");
	}
	%>
	<h1>Listar seguros</h1>
	<form action="ServletListadoSeguros">
	Filtrar por tipo:<select name="TipoSeguro">
		<option value="0">--todos--</option>
    		</select>
    		<input type="submit" value="Filtrar" name="btnFiltrar">
    		<input type="submit" value="Mostrar todos" name="btnMostrarTodos">
    		<br>
    		<table border="1">
    		<tr><th>ID Seguro</th> <th>Descripcion</th> <th>ID Tipo</th> <th>Costo Contratacion</th> <th>Costo Asegurado</th> </tr>
    		<%
				if(ls!=null)
				for(Seguro s: ls){
			%>
			<tr> <td><%=s.getIdSeguro()%></td> <td><%=s.getDescripcion()%></td> <td><%=s.getIdTipo()%></td> <td><%=s.getCostoContratacion()%></td> <td><%=s.getCostoAsegurado()%></td>  
    		  <%
    		}
    		%>
    		</table>
	</form>
</body>
</html>