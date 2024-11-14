<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="WEB-INF/layouts/header.jsp"/>
<!-- Hero Section with Main Menu -->
<section class="hero-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <div class="menu-card">
                    <h1 class="mb-4 text-dark">Welcome to Modern Library</h1>
                    <p class="text-muted mb-5">Your gateway to knowledge and discovery</p>

                    <div class="d-flex flex-wrap justify-content-center">
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary menu-button m-2">
                            <i class="fas fa-sign-in-alt me-2"></i>Log In
                        </a>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-success menu-button m-2">
                            <i class="fas fa-user-plus me-2"></i>Register
                        </a>
                        <a href="${pageContext.request.contextPath}/books/all" class="btn btn-info menu-button m-2 text-white">
                            <i class="fas fa-book me-2"></i>View Books
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="WEB-INF/layouts/footer.jsp"/>
