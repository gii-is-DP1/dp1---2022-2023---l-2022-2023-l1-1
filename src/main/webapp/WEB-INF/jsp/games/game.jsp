<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="game">
    <h1><c:out value="${game.name}"/></h1>
    <c:if test = "${game.state == 'IN_PROCESS'}">
        <h1><c:out value="${game.round} ROUND"/></h1>
        <h1><c:out value="TURN ${turn.currentTurn}"/></h1>
        <h1><c:out value="${game.stage} STAGE"/></h1>
    </c:if>


    <c:if test = "${game.state == 'FINISHED'}">
        <br>
        <c:forEach items="${winnerPlayers}" var="winner">
            <c:if test = "${winner == currentPlayer}">
            <h1 style="color:rgb(45, 125, 4); font-size:75px;"> WINNER </h1>
            </c:if>
        </c:forEach>
        <c:forEach items="${loserPlayers}" var="loser">
            <c:if test = "${loser == currentPlayer}">
                <h1 style="color:red; font-size:75px;"> LOSER </h1>
            </c:if>
        </c:forEach>
        <br>
    </c:if>

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
                    <c:out value="${game.suffragiumLimit}"/>
                </td>
            </tr>   
        </tbody>
    </table>
    <c:if test = "${game.state == 'IN_PROCESS'}">
        <c:if test = "${game.stage =='VOTING' && playerDeck.roleCard =='CONSUL' && roleCardNumber != 4}">
            <a class="btn btn-default" href="/games/${game.id}/rolesDesignation" height="120">Role designation</a>
        </c:if>
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
                    <c:if test="${playerInfo.spectator==false}">
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
                                                <c:if test="${turn.voteCount == 2 && game.stage == 'VOTING' && deck.voteCards.size() > 1}">
                                                    <img src="/resources/images/SelectedYellowVote.PNG" width="80" height="120"/>
                                                </c:if>
                                                <c:forEach var="votes" items="${deck.voteCards}">
                                                    <c:if test="${playerDeck.roleCard =='PRETOR' && game.stage =='VETO'}">
                                                        <c:choose>
                                                            <c:when test="${votes.type == 'YELLOW'}">
                                                                <a href="/games/${game.id}/forcedVoteChange/${deck.player.id}"> 
                                                                    <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>                            
                                                                </a>  
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="/games/${game.id}/pretorSelection/${votes.type}"> 
                                                                    <img src="/resources/images/reverse_card.PNG" width="80" height="120"/>                            
                                                                </a>  
                                                            </c:otherwise>
                                                        </c:choose>
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
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test = "${game.state == 'FINISHED'}">
        <table>
            <td>
            <h1>WINNER FACTION:<br> ${game.winners}</h1>
            <c:if test = "${game.winners == 'LOYALS'}">
                <img src="/resources/images/Loyal.PNG" width="200" height="300"/>          
            </c:if>
            <c:if test = "${game.winners == 'TRAITORS'}">
                <img src="/resources/images/Traitor.PNG" width="200" height="300"/>          
            </c:if>
            <c:if test = "${game.winners == 'MERCHANTS'}">
                <img src="/resources/images/Merchant.PNG" width="200" height="300"/>          
            </c:if>
            </td>
        <td>
            <h1>WINNERS</h1>
            <c:forEach items="${winnerPlayers}" var="winner">
                ${winner.user.username} <span class="glyphicon glyphicon-arrow-right"></span> Selected faction: 
                <c:forEach items="${winner.decks}" var="deck">
                    <c:if test = "${deck.game == game}">
                        ${deck.factionCards[0].type}<br>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </td>
        <td>
            <h1>LOSERS</h1> 
            <c:forEach items="${loserPlayers}" var="loser">
                ${loser.user.username} <span class="glyphicon glyphicon-arrow-right"></span> Selected faction: 
                <c:forEach items="${loser.decks}" var="deck">
                    <c:if test = "${deck.game == game}">
                        ${deck.factionCards[0].type}<br>
                    </c:if>
                </c:forEach>
            </c:forEach>

        </td>
        </table>
        <br>
        <a class="btn btn-default" href="/">EXIT</a>
    </c:if>
</petclinic:layout>