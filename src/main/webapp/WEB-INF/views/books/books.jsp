<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Book Management</h2>
    <a href="${pageContext.request.contextPath}/books/create" class="btn btn-primary">
      Create New Book
    </a>
  </div>

  <div class="card">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Autor</th>
            <th>ISBN</th>
            <th>Año Publicación</th>
            <th>Categoría</th>
            <c:if test="${sessionScope.loggedIn}">
              <th colspan="2">Acciones</th>
            </c:if>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${books}" var="book">
            <tr>
              <td><c:out value="${book.getBookId()}"/></td>
              <td><c:out value="${book.getTitle()}"/></td>
              <td><c:out value="${book.getAuthor().getFullName()}"/></td>
              <td><c:out value="${book.getIsbn()}"/></td>
              <td><c:out value="${book.getPublicationYear()}"/></td>
              <td><c:out value="${book.getCategory().getName()}"/></td>
              <c:if test="${sessionScope.loggedIn}">
              <td>
                <div class="btn-group" role="group">
                  <a href="${pageContext.request.contextPath}/books/edit?id=${book.bookId}"
                     class="btn btn-sm btn-warning">Edit</a>
                  <form method="post" action="${pageContext.request.contextPath}/books/delete" style="display:inline;"
                        onsubmit="return confirm('Are you sure you want to delete this record?')">
                    <input type="hidden" name="id" value="${book.bookId}"/>
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