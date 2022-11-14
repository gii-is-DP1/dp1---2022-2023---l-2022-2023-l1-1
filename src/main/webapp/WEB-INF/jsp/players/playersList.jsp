<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">
    <h2>Players</h2>
    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Spectator</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <c:out value="${player.spectator}"/>
                </td>
                <td>
                    <c:out value="${player.user.username}"/>
                </td>
                <td>
                    <a class="btn btn-default" href="/players/${player.id}/edit">Edit player</a>
                </td>
                <td>
                    <a class="btn btn-default" href="/players/${player.id}/delete">Delete player</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href="/users/new">Create new player</a>
</petclinic:layout>
