<!-- WEB-INF/views/role_permissions/form-roles_permission.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">${action == 'edit' ? 'Edit' : 'Create'} Role Permission</h4>
        </div>
        <div class="card-body">
          <!-- Display global errors if any -->
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}"/>
            </div>
          </c:if>

          <form method="post" action="${pageContext.request.contextPath}/role_permissions/${action}" class="needs-validation" novalidate>
            <c:if test="${action == 'edit'}">
              <input type="hidden" name="id" value="${rolePermission.role.roleId}">
            </c:if>
            <input type="hidden" name="_csrf" value="${csrfToken}"/>

            <div class="mb-3">
              <label for="roleId" class="form-label">Rol</label>
              <select class="form-select ${not empty errors.roleId ? 'is-invalid' : ''}"
                      id="roleId"
                      name="roleId"
                      required>
                <option value="">Selecciona un rol</option>
                <c:forEach items="${roles}" var="role">
                  <option value="${role.roleId}"
                    ${rolePermission.role.roleId == role.roleId ? 'selected' : ''}>
                      <c:out value="${role.name}"/>
                  </option>
                </c:forEach>
              </select>
              <c:if test="${not empty errors.roleId}">
                <div class="invalid-feedback">
                    <c:out value="${errors.roleId}"/>
                </div>
              </c:if>
            </div>

            <div class="mb-3">
              <label for="permissionId" class="form-label">Permiso</label>
              <select class="form-select ${not empty errors.permissionId ? 'is-invalid' : ''}"
                      id="permissionId"
                      name="permissionId"
                      required>
                <option value="">Selecciona un permiso</option>
                <c:forEach items="${permissions}" var="permission">
                  <option value="${permission.permissionId}"
                    ${rolePermission.permission.permissionId == permission.permissionId ? 'selected' : ''}>
                      <c:out value="${permission.name}"/>
                  </option>
                </c:forEach>
              </select>
              <c:if test="${not empty errors.permissionId}">
                <div class="invalid-feedback">
                    <c:out value="${errors.permissionId}"/>
                </div>
              </c:if>
            </div>

            <div class="d-flex justify-content-end gap-2">
              <a href="${pageContext.request.contextPath}/role_permissions/all"
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
