<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Author Management</h2>
    <a href="${pageContext.request.contextPath}/authors/create" class="btn btn-primary">
      Create New Author
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
            <th>Apellido</th>
            <th>Nombre Completo</th>
            <c:if test="${sessionScope.loggedIn}">
              <th colspan="2">Acciones</th>
            </c:if>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${authors}" var="a">
            <tr>
              <td><c:out value="${a.authorId}"/></td>
              <td><c:out value="${a.name}"/></td>
              <td><c:out value="${a.surname}"/></td>
              <td><c:out value="${a.fullName}"/></td>
              <c:if test="${sessionScope.loggedIn}">
                <td>
                  <div class="btn-group" role="group">
                    <a href="${pageContext.request.contextPath}/authors/edit?id=${a.authorId}"
                       class="btn btn-sm btn-warning">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/authors/delete" style="display:inline;"
                          onsubmit="return confirm('Are you sure you want to delete this record?')">
                      <input type="hidden" name="id" value="${a.authorId}"/>
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