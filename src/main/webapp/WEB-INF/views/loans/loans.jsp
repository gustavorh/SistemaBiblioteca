<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Loan Management</h2>
        <a href="${pageContext.request.contextPath}/loans/create" class="btn btn-primary">
            Create New Loan
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título Libro</th>
                        <th>Nombre Miembro</th>
                        <th>Fecha Desde</th>
                        <th>Fecha Hasta</th>
                        <th>Fecha Devolución</th>
                        <c:if test="${sessionScope.loggedIn}">
                            <th colspan="2">Acciones</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${loans}" var="l">
                        <tr>
                            <td><c:out value="${l.loanId}"/></td>
                            <td><c:out value="${l.book.title}"/></td>
                            <td><c:out value="${l.user.userName}"/></td>
                            <td><c:out value="${l.member.name}"/> <c:out value="${l.member.paternalSurname}"/> <c:out value="${l.member.maternalSurname}"/></td>
                            <td><c:out value="${l.loanDate}"/></td>
                            <td><c:out value="${l.dueDate}"/></td>
                            <td><c:out value="${l.returnDate}"/></td>
                            <c:if test="${sessionScope.loggedIn}">
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="${pageContext.request.contextPath}/loans/edit?id=${l.loanId}"
                                           class="btn btn-sm btn-warning">Edit</a>
                                        <form method="post" action="${pageContext.request.contextPath}/loans/delete" style="display:inline;"
                                              onsubmit="return confirm('Are you sure you want to delete this record?')">
                                          <input type="hidden" name="id" value="${l.loanId}"/>
                                          <input type="hidden" name="_csrf" value="${csrfToken}"/>
                                          <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                        </form>
                                    </div>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/layouts/footer.jsp" />