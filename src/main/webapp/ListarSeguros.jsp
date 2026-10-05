<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de seguros</title>
</head>
<body>
    <jsp:include page="Menu.jsp"></jsp:include>

    <h1>Listar seguros</h1>

    <c:if test="${not empty errorMensaje}">
        <div style="color: red; background-color: #ffe6e6; padding: 10px; border: 1px solid red; margin-bottom: 15px;">
            <strong>¡Atención!</strong> ${errorMensaje}
        </div>
    </c:if>

    <form action="ServletListadoSeguros" method="get">
        <label for="ddlTipoSeguro">Filtrar por tipo de seguro:</label>
        <select name="ddlTipoSeguro" id="ddlTipoSeguro">
            <option value="-1">-- Seleccione una opción --</option>
            <c:forEach var="ts" items="${listaTipoSeguros}">
                <option value="${ts.idTipo}"
                    <c:if test="${ts.idTipo == idTipoSeleccionado}">selected</c:if>>
                    ${ts.descripcion}
                </option>
            </c:forEach>
        </select>

        <input type="submit" value="Filtrar" name="btnFiltrar">
        <input type="submit" value="Mostrar todos" name="btnMostrarTodos">
    </form>

    <br>

    <table border="1">
        <tr>
            <th>ID Seguro</th>
            <th>Descripción</th>
            <th>Tipo de Seguro</th>
            <th>Costo de Contratación</th>
            <th>Costo Máximo Asegurado</th>
        </tr>
        <c:forEach var="seguro" items="${listaSeguros}">
            <tr>
                <td>${seguro.idSeguro}</td>
                <td>${seguro.descripcion}</td>
                <td>${seguro.descripcionTipoSeguro}</td>
                <td>${seguro.costoContratacion}</td>
                <td>${seguro.costoAsegurado}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
