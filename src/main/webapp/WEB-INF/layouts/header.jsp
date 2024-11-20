<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Library Management System</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Library System</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
            Manage
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/books/all">Books</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/members/all">Members</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/users/all">Users</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/loans/all">Loans</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/authors/all">Authors</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories/all">Categories</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/statuses/all">Statuses</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/roles/all">Roles</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/permissions/all">Permissions</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/role_permissions/all">RolePermissions</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/role_users/all">RoleUsers</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
            Reports
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/reports/activity">Member Activity Report</a></li>
          </ul>
        </li>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${sessionScope.username != null}">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                  ${sessionScope.username}
              </a>
              <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profile</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/logout">Logout</a></li>
              </ul>
            </li>
          </c:when>
          <c:otherwise>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                Account
              </a>
              <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/login">Login</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/register">Register</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/guest">Continue as Guest</a></li>
              </ul>
            </li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>