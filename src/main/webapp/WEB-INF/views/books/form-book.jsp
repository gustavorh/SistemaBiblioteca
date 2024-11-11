<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Gustavo
  Date: 10/11/2024
  Time: 11:29 pm
  To change this template use File | Settings | File Templates.
--%>
<head>
    <title>${action == 'edit' ? 'Edit' : 'Add'} Book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<jsp:include page="/WEB-INF/layouts/header.jsp"/>
<h3>${action == 'edit' ? 'Edit' : 'Add'} Book</h3>

<form method="post" action="${pageContext.request.contextPath}/books/${action}">
    <c:if test="${action == 'edit'}">
        <input type="hidden" name="id" value="${book.bookId}">
    </c:if>

    <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" value="${book.title}" required>
        <c:if test="${not empty errors.title}">
            <span class="error">${errors.title}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="isbn">ISBN:</label>
        <input type="text" id="isbn" name="isbn" value="${book.isbn}" required>
        <c:if test="${not empty errors.isbn}">
            <span class="error">${errors.isbn}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="publicationYear">Publication Year:</label>
        <input type="number" id="publicationYear" name="publicationYear"
               value="${book.publicationYear}" required>
        <c:if test="${not empty errors.publicationYear}">
            <span class="error">${errors.publicationYear}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="authorId">Author:</label>
        <select id="authorId" name="authorId" required>
            <option value="">Select an author</option>
            <c:forEach items="${authors}" var="author">
                <option value="${author.authorId}"
                    ${book.author.authorId == author.authorId ? 'selected' : ''}>
                        ${author.fullName}
                </option>
            </c:forEach>
        </select>
        <c:if test="${not empty errors.authorId}">
            <span class="error">${errors.authorId}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="categoryId">Category:</label>
        <select id="categoryId" name="categoryId" required>
            <option value="">Select a category</option>
            <c:forEach items="${categories}" var="category">
                <option value="${category.categoryId}"
                    ${book.category.categoryId == category.categoryId ? 'selected' : ''}>
                        ${category.name}
                </option>
            </c:forEach>
        </select>
        <c:if test="${not empty errors.categoryId}">
            <span class="error">${errors.categoryId}</span>
        </c:if>
    </div>

    <div class="form-actions">
        <button type="submit">Save</button>
        <a href="${pageContext.request.contextPath}/books" class="button">Cancel</a>
    </div>
</form>
<jsp:include page="/WEB-INF/layouts/footer.jsp"/>
