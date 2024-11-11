<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<h3 class="text-center mb-4">${title}</h3>
<table class="table table-striped table-hover">
    <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Autor</th>
        <th>ISBN</th>
        <th>Año Publicación</th>
        <th>Categoría</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="b">
        <tr>
            <td>${b.getBookId()}</td>
            <td>${b.getTitle()}</td>
            <td>${b.getAuthor().getFullName()}</td>
            <td>${b.getIsbn()}</td>
            <td>${b.getPublicationYear()}</td>
            <td>${b.getCategory().getName()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/WEB-INF/layouts/footer.jsp"/>