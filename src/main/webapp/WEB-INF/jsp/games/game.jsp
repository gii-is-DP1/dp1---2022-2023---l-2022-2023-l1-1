<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="game">
    <h1><c:out value="${game.name}"/></h1>
    <table id="suffragium" class="table table-striped">
        <h4>Suffragium</h4>
        <thead>
        <tr>
            <th>Loyals votes</th>
            <th>Traitor votes</th>
            <th>Vote limit</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="${suffragiumCard.loyalsVotes}"/>
                </td>
                <td>
                    <c:out value="${suffragiumCard.traitorsVotes}"/>
                </td>
                <td>
                    <c:out value="${suffragiumCard.voteLimit}"/>
                </td>
            </tr>
        </tbody>
    </table>
</petclinic:layout>
