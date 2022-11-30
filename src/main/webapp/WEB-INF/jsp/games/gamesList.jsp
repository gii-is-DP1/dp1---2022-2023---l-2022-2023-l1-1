<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Public</th>
            <th>Number of players</th>
            <th>Date</th>
            <th>Duration</th>
            <th>Winners</th>
            <th>Join game</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.name}"/>
                </td>
                <td>
                    <c:out value="${game.publicGame}"/>
                </td>
                <td>
                    <c:out value="${game.numPlayers}"/>
                </td>
                <td>
                    <c:out value="${game.date}"/>
                </td>
                <td>
                    <c:out value="${game.duration}"/>
                </td>
                <td>
                    <c:out value="${game.winners}"/>
                </td>
                <td>
                <sec:authorize access="hasAuthority('player')">
                    <a href="/games/${game.id}/lobby"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Join game</a>
                </sec:authorize>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
