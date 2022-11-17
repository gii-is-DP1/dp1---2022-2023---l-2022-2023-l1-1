<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="home">
    <sec:authorize access="hasAuthority('player')">
        <a class="btn btn-default" href="/games/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create game</a>
        <a class="btn btn-default" href="/games/starting/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Join a game</a>
    </sec:authorize>
    <sec:authorize access="hasAuthority('admin')">
        <a class="btn btn-default" href="/games/history/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Games history</a>
        <a class="btn btn-default" href="/games/inProcess/find"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Games in process</a>
        <a class="btn btn-default" href="/players"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Manage players</a>
    </sec:authorize>
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <h2> Project: ${title}</h2>
        <p><h2> Group: ${group}</h2></p>
        <p><ul>
            <c:forEach items="${persons}" var="person">
                <li>${person.firstName}&nbsp${person.lastName}</li>
            </c:forEach>
        </ul></p>
    </div>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/logoUS.png" htmlEscape="true" var="logoImage"/>
            <img class="img-responsive" src="${logoImage}"/>
            <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
</petclinic:layout>
