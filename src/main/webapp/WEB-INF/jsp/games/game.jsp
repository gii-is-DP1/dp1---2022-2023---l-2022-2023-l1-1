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
            <th>Role Card</th>
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
                            <c:if test="${deck.game.id == game.id}">
                                <c:if test="${deck.roleCardImg != NO_ROLE}">
                                    <a href="/"> 
                                        <img src="${deck.getRoleCardImg()}" width="80" height="120" />                            
                                    </a>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        
                    </td>
                    

                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:if test="${deck.game.id == game.id}">
                                <c:choose>
                                    <c:when test="${deck.player.id == actualPlayer.id}">
                                        <c:forEach var="factions" items="${deck.factionCards}">
                                            <a href="/games/${game.id}/edit/${factions.type}"> 
                                                <img src="${factions.card}" width="80" height="120"/>                            
                                            </a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="factions" items="${deck.factionCards}">
                                            <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>                               
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                
                            </c:if>
                        </c:forEach>
                        
                    </td>
                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:if test="${deck.game.id == game.id}">
                                <c:choose>
                                    <c:when test="${deck.player.id == actualPlayer.id}">
                                        <c:forEach var="votes" items="${deck.voteCards}">
                                            <a href="/games/${game.id}/updateSuffragium/${votes.type}"> 
                                                <img src="${votes.card}" width="80" height="120"/>                            
                                            </a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="votes" items="${deck.voteCards}">
                                            <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>                               
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                        
                    </td>
                </tr>

            </c:forEach>
        </tbody>
</petclinic:layout>
