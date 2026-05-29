<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Permissions Management</h2>
        <a href="${pageContext.request.contextPath}/permissions/create" class="btn btn-primary">
            Create New Permission
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripcion</th>
                        <c:if test="${sessionScope.loggedIn}">
                            <th colspan="2">Acciones</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${permissions}" var="p">
                        <tr>
                            <td><c:out value="${p.permissionId}"/></td>
                            <td><c:out value="${p.name}"/></td>
                            <td><c:out value="${p.description}"/></td>
                            <c:if test="${sessionScope.loggedIn}">
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="${pageContext.request.contextPath}/permissions/edit?id=${p.permissionId}"
                                           class="btn btn-sm btn-warning">Edit</a>
                                        <form method="post" action="${pageContext.request.contextPath}/permissions/delete" style="display:inline;"
                                              onsubmit="return confirm('Are you sure you want to delete this record?')">
                                          <input type="hidden" name="id" value="${p.permissionId}"/>
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