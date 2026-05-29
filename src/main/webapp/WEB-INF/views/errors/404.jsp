<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<div class="container mt-5 text-center">
  <h2>404 — Página no encontrada</h2>
  <p class="text-muted">El recurso solicitado no existe.</p>
  <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Volver al inicio</a>
</div>
<jsp:include page="/WEB-INF/layouts/footer.jsp" />
