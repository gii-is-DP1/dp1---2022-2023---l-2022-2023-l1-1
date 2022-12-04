<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">
    <jsp:body>
        <h2>
            <c:choose>
                <c:when test="${user['new']}">Register User </c:when> 
                <c:otherwhise> Update User </c:otherwhise>
            </c:choose>
            
        </h2>
        <form:form modelAttribute="user"
                   class="form-horizontal">
            <input type="hidden" name="username" value="${user.username}"/>
            <div class="form-group has-feedback">
                <petclinic:inputField label="Username" name="username"/>
                <petclinic:inputField label="Password" name="password"/>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${user['new']}">
                            <button class="btn btn-default" type="submit">Register</button>
                            <a class="btn btn-default" href="/users">Cancel</a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update user</button>
                            <a class="btn btn-default" href="/users">Cancel</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>