<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<h3 class="text-center mb-4">${title}</h3>
<table class="table table-striped table-hover">
    <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Nombre Completo</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${authors}" var="a">
        <tr>
            <td>${a.authorId}</td>
            <td>${a.name}</td>
            <td>${a.surname}</td>
            <td>${a.fullName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/WEB-INF/layouts/footer.jsp"/>