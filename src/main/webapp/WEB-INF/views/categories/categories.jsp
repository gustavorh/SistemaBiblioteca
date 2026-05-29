<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Categories Management</h2>
    <a href="${pageContext.request.contextPath}/categories/create" class="btn btn-primary">
      Create New Category
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
            <c:if test="${sessionScope.loggedIn}">
              <th colspan="2">Acciones</th>
            </c:if>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${categories}" var="c">
            <tr>
              <td><c:out value="${c.categoryId}"/></td>
              <td><c:out value="${c.name}"/></td>
              <c:if test="${sessionScope.loggedIn}">
                <td>
                  <div class="btn-group" role="group">
                    <a href="${pageContext.request.contextPath}/categories/edit?id=${c.categoryId}"
                       class="btn btn-sm btn-warning">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/categories/delete" style="display:inline;"
                          onsubmit="return confirm('Are you sure you want to delete this record?')">
                      <input type="hidden" name="id" value="${c.categoryId}"/>
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