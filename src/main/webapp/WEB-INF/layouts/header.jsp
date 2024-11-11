<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        .hero-section {
            background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),
            url('https://images.unsplash.com/photo-1507842217343-583bb7270b66?ixlib=rb-1.2.1');
            background-size: cover;
            background-position: center;
            height: 80vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        .menu-card {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 15px;
            padding: 2rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .menu-button {
            width: 200px;
            margin: 10px;
            padding: 15px;
            font-size: 1.1rem;
            transition: transform 0.2s;
        }

        .menu-button:hover {
            transform: translateY(-3px);
        }

        .footer {
            background-color: #333;
            color: white;
            padding: 20px 0;
            position: relative;
            bottom: 0;
            width: 100%;
        }
    </style>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/books/ver">Books</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/authors">Authors</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/categories">Categories</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/loans">Loans</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/members">Members</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${pageContext.request.contextPath}/users">Users</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container mt-5">