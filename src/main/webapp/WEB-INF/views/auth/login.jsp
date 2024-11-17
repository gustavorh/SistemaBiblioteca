<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>

<h3 class="text-center mb-4">${title}</h3>
<form action="${pageContext.request.contextPath}/auth/login" method="post">
    <div class="row my-2">
        <label for="username" class="form-label">Username</label>
        <div>
            <input type="text" name="username" id="username" class="form-control">
        </div>
    </div>
    <div class="row my-2">
        <label for="password" class="form-label">Password</label>
        <div>
            <input type="password" name="password" id="password" class="form-control">
        </div>
    </div>
    <div class="row my-2">
        <input type="submit" value="Login" class="btn btn-primary">
    </div>
</form>

<jsp:include page="/WEB-INF/layouts/footer.jsp"/>
