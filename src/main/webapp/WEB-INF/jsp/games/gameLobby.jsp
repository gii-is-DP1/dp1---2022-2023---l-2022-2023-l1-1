<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lobbies">
    <h2><c:out value="${game.name}"/> lobby</h2>
    <td>
        <c:out value="Number of players: ${game.numPlayers}/8"/>
    </td>
    <table id="creatorTable" class="table table-striped">
        <thead>
        <tr>
            <th>Creator</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerInfos}" var="playerInfo">
            <c:if test="${playerInfo.creator}">
                <tr>
                    <td>
                        <c:out value="${playerInfo.player.user.username}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <table id="creatorTable" class="table table-striped">
        <thead>
        <tr>
            <th>Players</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerInfos}" var="playerInfo">
            <c:if test="${playerInfo.spectator == false}">
                <tr>
                    <td>
                        <c:out value="${playerInfo.player.user.username}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <table id="creatorTable" class="table table-striped">
        <thead>
        <tr>
            <th>Spectators</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerInfos}" var="playerInfo">
            <c:if test="${playerInfo.spectator}">
                <tr>
                    <td>
                        <c:out value="${playerInfo.player.user.username}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href="/games/${game.id}">Start game</a>
</petclinic:layout>
