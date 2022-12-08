<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Users</h2>
    
    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Password</th>
            <th>Enabled</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <c:out value="${user.username}"/>
                </td>
                <td>
                    <c:out value="${user.password}"/>
                </td>
                <td>
                    <c:out value="${user.enabled}"/>
                </td> 
                <td>
                    <a class="btn btn-default" href="/users/${user.username}/delete">Delete</a>
                </td>
                <td>
                    <a class="btn btn-default" href="/users/${user.username}/edit">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href="/users/create">Create new User</a>
</petclinic:layout>
