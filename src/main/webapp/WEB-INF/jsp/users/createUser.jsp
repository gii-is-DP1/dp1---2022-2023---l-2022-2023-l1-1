<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>
        <c:if test="${user['new']}">Register </c:if> User
    </h2>
    <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Username" name="username"/>
            <petclinic:inputField label="Password" name="password"/>
        </div>
        <div class="form-group">
            <c:choose>
                <c:when test="${user['new']}">
                        <button class="btn btn-default" type="submit">Register</button>
                        <a class="btn btn-default" href="/users">Cancel</a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-default" type="submit">Save</button>
                    <a class="btn btn-default" href="/users">Cancel</a>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
</petclinic:layout>