<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<div class="container">
    <h2>${reportTitle}</h2>
    <table class="table">
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
                <td>${member.rut}</td>
                <td>${member.fullName}</td>
                <td>${member.state}</td>
                <td>${member.registrationDate}</td>
                <td>${member.totalHistoricalLoans}</td>
                <td>${member.overdueLoans}</td>
                <td>${member.activeLoans}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Optional: Add export buttons -->
    <div class="mt-3">
        <button onclick="exportToPDF()" class="btn btn-primary">Export to PDF</button>
        <button onclick="exportToExcel()" class="btn btn-success">Export to Excel</button>
    </div>
</div>

<jsp:include page="/WEB-INF/layouts/footer.jsp"/>