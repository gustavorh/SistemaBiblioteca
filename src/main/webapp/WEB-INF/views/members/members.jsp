<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Member Management</h2>
        <a href="${pageContext.request.contextPath}/members/create" class="btn btn-primary">
            Create New Member
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>RUT</th>
                        <th>Usuario</th>
                        <th>Nombre Completo</th>
                        <th>Fecha Inscripción</th>
                        <th>Estado</th>
                        <c:if test="${sessionScope.loggedIn}">
                            <th colspan="2">Acciones</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${members}" var="m">
                        <tr>
                            <td><c:out value="${m.memberId}"/></td>
                            <td><c:out value="${m.rut}"/></td>
                            <td><c:out value="${m.user.userName}"/></td>
                            <td><c:out value="${m.name}"/> <c:out value="${m.paternalSurname}"/> <c:out value="${m.maternalSurname}"/></td>
                            <td><c:out value="${m.registrationDate}"/></td>
                            <td><c:out value="${m.status.name}"/></td>
                            <c:if test="${sessionScope.loggedIn}">
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="${pageContext.request.contextPath}/members/edit?id=${m.memberId}"
                                           class="btn btn-sm btn-warning">Edit</a>
                                        <form method="post" action="${pageContext.request.contextPath}/members/delete" style="display:inline;"
                                              onsubmit="return confirm('Are you sure you want to delete this record?')">
                                          <input type="hidden" name="id" value="${m.memberId}"/>
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