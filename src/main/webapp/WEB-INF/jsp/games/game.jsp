<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="game">
    <h1><c:out value="${game.name}"/></h1>
    <h1><c:out value="${game.round} ROUND"/></h1>
    <h1><c:out value="TURN ${turn.currentTurn}"/></h1>
    <h1><c:out value="${game.stage} STAGE"/></h1>
    
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
                                        <img src="${deck.getRoleCardImg()}" width="80" height="120" />                            
                                </c:if>
                            </c:if>
                        </c:forEach>
                        
                    </td>
                    

                    <td>
                        <c:forEach var="deck" items="${playerInfo.player.decks}">
                            <c:if test="${deck.game.id == game.id}">
                                <c:choose>
                                    <c:when test="${deck.player.id == currentPlayer.id}">
                                        <c:forEach var="factions" items="${deck.factionCards}">
                                            <c:if test="${game.stage =='END_OF_TURN' && playerDeck.roleCard =='CONSUL'}">
                                                <a href="/games/${game.id}/edit/${factions.type}"> 
                                                    <img src="${factions.card}" width="80" height="120"/>                            
                                                </a>
                                            </c:if>
                                            <c:if test="${game.stage != 'END_OF_TURN' || playerDeck.roleCard != 'CONSUL'}">
                                                <img src="${factions.card}" width="80" height="120"/>
                                            </c:if>
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
                                    <c:when test="${deck.player.id == currentPlayer.id}">
                                        <c:forEach var="votes" items="${deck.voteCards}">
                                            <c:if test="${game.stage =='VOTING' && deck.voteCardsNumber > 1}">
                                                <a href="/games/${game.id}/updateVotes/${votes.type}"> 
                                                    <img src="${votes.card}" width="80" height="120"/>                            
                                                </a>
                                            </c:if>
                                            <c:if test="${game.stage != 'VOTING' || deck.voteCardsNumber == 1}">
                                                <img src="${votes.card}" width="80" height="120"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="votes" items="${deck.voteCards}">
                                            <c:if test="${playerDeck.roleCard =='PRETOR' && game.stage =='VETO'}">
                                                <a href="/games/${game.id}/pretorSelection/${votes.type}"> 
                                                    <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>                            
                                                </a>   
                                            </c:if>
                                            <c:if test="${playerDeck.roleCard !='PRETOR' || game.stage !='VETO'}">
                                                <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>
                                            </c:if>                          
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
