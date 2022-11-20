<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="home">
    <sec:authorize access="!isAuthenticated()">
		<p>Log in or sing up to start playing</p>
	</sec:authorize>
    <sec:authorize access="hasAuthority('player')">
        <a class="btn btn-default" href="/games/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create game</a>
        <a class="btn btn-default" href="/games/starting/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Join a game</a>
        <a class="btn btn-default" href="/games/playerHistory/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Your game history</a>
    </sec:authorize>
    <sec:authorize access="hasAuthority('admin')">
        <a class="btn btn-default" href="/games/history/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Games history</a>
        <a class="btn btn-default" href="/games/inProcess/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Games in process</a>
        <a class="btn btn-default" href="/players"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Manage players</a>
    </sec:authorize>
</petclinic:layout>
