<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<div class="container mt-5 text-center">
  <h2>500 — Error interno del servidor</h2>
  <p class="text-muted">Ocurrió un error inesperado. Por favor, inténtalo de nuevo más tarde.</p>
  <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Volver al inicio</a>
</div>
<jsp:include page="/WEB-INF/layouts/footer.jsp" />
