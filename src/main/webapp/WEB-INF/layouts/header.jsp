<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">
            <i class="fas fa-book-reader me-2"></i>Library
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/books/all">Books</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/authors/all">Authors</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/categories/all">Categories</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/loan/alls">Loans</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/members/all">Members</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/users/all">Users</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container mt-5">