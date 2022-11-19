<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lobbies">
    <h2>Lobby</h2>
    <td>
        <c:out value="${game.name}"/>
    </td>
    <table id="lobbiesTable" class="table table-striped">
        <thead>
        <tr>
            <th>List of players</th>
            <th>Creator</th>
            <th>Spectator</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerInfos}" var="playerInfo">
            <tr>
                <td>
                    <c:out value="${playerInfo.player.user.username}"/>
                </td>
                <td>
                    <c:out value="${playerInfo.creator}"/>
                </td>
                <td>
                    <c:out value="${playerInfo.spectator}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
