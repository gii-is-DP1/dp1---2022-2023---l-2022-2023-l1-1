Skip to content
Search or jump to…
Pull requests
Issues
Codespaces
Marketplace
Explore
 
@gahijil 
gii-is-DP1
/
dp1-2022-2023-l1-1
Public
generated from gii-is-DP1/spring-petclinic
Code
Issues
19
Pull requests
Discussions
Actions
Projects
1
Wiki
Security
Insights
Settings
dp1-2022-2023-l1-1/src/main/webapp/WEB-INF/jsp/games/gameLobby.jsp
@alvgonfri
alvgonfri Initial deck assignment
Latest commit caf9b65 4 days ago
 History
 2 contributors
@alvgonfri@gahijil
72 lines (71 sloc)  2.27 KB

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
    <c:if test="${game.numPlayers >= 5 && game.numPlayers <= 8}">
        <a class="btn btn-default" href="/games/${game.id}">Start game</a>
    </c:if>
    <c:if test="${game.numPlayers < 5}">
        <p>Waiting for more players to start the game</p>
    </c:if>
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
</petclinic:layout>
