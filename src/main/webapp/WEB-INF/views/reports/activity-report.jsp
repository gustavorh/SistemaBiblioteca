<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp" />
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Reports Management</h2>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>RUT</th>
                        <th>Nombre Completo</th>
                        <th>Estado</th>
                        <th>Fecha Inscripción</th>
                        <th>Total Préstamos</th>
                        <th>Préstamos Atrasados</th>
                        <th>Préstamos Activos</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${reportData}" var="member">
                    <tr>
                        <td><c:out value="${member.rut}"/></td>
                        <td><c:out value="${member.fullName}"/></td>
                        <td><c:out value="${member.state}"/></td>
                        <td><c:out value="${member.registrationDate}"/></td>
                        <td><c:out value="${member.totalHistoricalLoans}"/></td>
                        <td><c:out value="${member.overdueLoans}"/></td>
                        <td><c:out value="${member.activeLoans}"/></td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/layouts/footer.jsp" />