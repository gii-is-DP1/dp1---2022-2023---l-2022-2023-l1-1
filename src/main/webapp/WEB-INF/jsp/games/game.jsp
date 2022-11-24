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

    <table id="decks" class="table table-striped">
        <h4>Decks</h4>
        <thead>
        <tr>
            <th>Player</th>
            <th>Role Cards</th>
            <th>Faction Cards</th>
            <th>Vote Cards</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${playerInfos}" var="playerInfo">
                <tr>
                    <td>
                        <c:out value="${playerInfo.player.user.username}"/><br>
                    </td>
                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:if test="${deck.roleCardImg != NO_ROLE}">
                                <a href="/"> 
                                    <img src="${deck.roleCardImg}" width="80" height="120" />                            
                                </a>
                            </c:if>
                        </c:forEach>
                        
                    </td>
                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:forEach var="factions" items="${deck.factionCards}">
                                <a href="/decks/edit/${factions.type}"> 
                                    <img src="${factions.card}" width="80" height="120"/>                            
                                </a>
                                
                            </c:forEach>
                            
                        </c:forEach>
                        
                    </td>
                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:forEach var="votes" items="${deck.voteCards}">
                                <a href="/"> 
                                    <img src="${votes.card}" width="80" height="120"/>                            
                                </a>
                                
                            </c:forEach>
                            
                        </c:forEach>
                        
                    </td>
                </tr>

            </c:forEach>
        </tbody>
</petclinic:layout>
