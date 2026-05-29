<!-- WEB-INF/views/categories/form-category.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">${action == 'edit' ? 'Edit' : 'Create'} Category</h4>
        </div>
        <div class="card-body">
          <!-- Display global errors if any -->
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}"/>
            </div>
          </c:if>

          <form method="post" action="${pageContext.request.contextPath}/categories/${action}" class="needs-validation" novalidate>
            <c:if test="${action == 'edit'}">
              <input type="hidden" name="id" value="${category.categoryId}">
            </c:if>
            <input type="hidden" name="_csrf" value="${csrfToken}"/>

            <div class="mb-3">
              <label for="name" class="form-label">Nombre</label>
              <input type="text"
                     class="form-control ${not empty errors.name ? 'is-invalid' : ''}"
                     id="name"
                     name="name"
                     value="<c:out value='${category.name}'/>"
                     required>
              <c:if test="${not empty errors.name}">
                <div class="invalid-feedback">
                    <c:out value="${errors.name}"/>
                </div>
              </c:if>
            </div>

            <div class="d-flex justify-content-end gap-2">
              <a href="${pageContext.request.contextPath}/categories/all"
                 class="btn btn-secondary">Cancel</a>
              <button type="submit" class="btn btn-primary">Save</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Custom validation script -->
<script>
  // Enable Bootstrap form validation
  (function () {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
              form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                  event.preventDefault()
                  event.stopPropagation()
                }
                form.classList.add('was-validated')
              }, false)
            })
  })()
</script>

<jsp:include page="/WEB-INF/layouts/footer.jsp"/>
