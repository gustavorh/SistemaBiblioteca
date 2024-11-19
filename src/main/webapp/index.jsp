<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="WEB-INF/layouts/header.jsp" />
<div class="container mt-5">
    <div class="jumbotron text-center">
        <h1 class="display-4">Welcome to Library Management System</h1>
        <p class="lead">Efficiently manage your library's resources and operations</p>
        <hr class="my-4">
        <p>Access our comprehensive library management tools to handle books, members, loans, and more.</p>
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/books/all" role="button">Browse Books</a>
    </div>

    <div class="row mt-5">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Book Management</h5>
                    <p class="card-text">Manage your library's book collection efficiently.</p>
                    <a href="${pageContext.request.contextPath}/books/all" class="btn btn-primary">Manage Books</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Member Management</h5>
                    <p class="card-text">Handle member registrations and accounts.</p>
                    <a href="${pageContext.request.contextPath}/members/all" class="btn btn-primary">Manage Members</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Loan Management</h5>
                    <p class="card-text">Track book loans and returns.</p>
                    <a href="${pageContext.request.contextPath}/loans/all" class="btn btn-primary">Manage Loans</a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="WEB-INF/layouts/footer.jsp" />